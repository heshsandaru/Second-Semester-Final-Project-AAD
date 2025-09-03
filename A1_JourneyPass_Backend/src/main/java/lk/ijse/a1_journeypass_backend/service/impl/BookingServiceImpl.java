package lk.ijse.a1_journeypass_backend.service.impl;

import jakarta.mail.MessagingException;
import lk.ijse.a1_journeypass_backend.dto.BookingDTO;
import lk.ijse.a1_journeypass_backend.dto.PassengerDTO;
import lk.ijse.a1_journeypass_backend.entity.*;
import lk.ijse.a1_journeypass_backend.repo.BookingRepo;
import lk.ijse.a1_journeypass_backend.repo.PassengerRepo;
import lk.ijse.a1_journeypass_backend.repo.ScheduleRepo;
import lk.ijse.a1_journeypass_backend.service.BookingService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PassengerRepo passengerRepo;

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private QrCodeGeneratorService qrCodeGeneratorService;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public boolean addBooking(BookingDTO bookingDTO) {
        if (bookingDTO.getBookingId() != null && bookingRepo.existsById(bookingDTO.getBookingId())) {
            System.out.println("Booking already exists: " + bookingDTO.getBookingId());
            return false;
        }

        Passenger passenger = passengerRepo.findById(bookingDTO.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        Schedule schedule = scheduleRepo.findById(bookingDTO.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Booking booking = new Booking();
        booking.setPassenger(passenger);
        booking.setSchedule(schedule);
        booking.setSeatsNumber(bookingDTO.getSeatsNumber());
        booking.setSeatPrice(bookingDTO.getSeatPrice());
        booking.setBookingStatus(bookingDTO.getBookingStatus());
        booking.setPaymentStatus(bookingDTO.getPaymentStatus());
        booking.setBookedAt(bookingDTO.getBookedAt());

        bookingRepo.save(booking);
        System.out.println("Booking added: " + bookingDTO);
        System.out.println("Booking saved successfully");
        return true;
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepo.findAll();

        return bookings.stream().map(booking -> {
            BookingDTO dto = new BookingDTO();
            dto.setBookingId(booking.getBookingId());
            dto.setPassengerId(booking.getPassenger().getNic());

            // ✅ Fix: set scheduleId manually from the entity
            if (booking.getSchedule() != null) {
                dto.setScheduleId(booking.getSchedule().getSchedule_id());
            }

            dto.setSeatsNumber(booking.getSeatsNumber());
            dto.setSeatPrice(booking.getSeatPrice());
            dto.setBookingStatus(booking.getBookingStatus());
            dto.setPaymentStatus(booking.getPaymentStatus());
            dto.setBookedAt(booking.getBookedAt());

            return dto;
        }).toList();
    }

    @Override
    public List<BookingDTO> getBookingsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Booking> bookings = bookingRepo.findAllByBookedAtBetween(startOfDay, endOfDay);
        return modelMapper.map(bookings, new TypeToken<List<BookingDTO>>() {}.getType());
    }

    @Override
    @Transactional
    public void savePassengerAndBooking(PassengerDTO passengerDTO, BookingDTO bookingDTO) {
        if (passengerDTO.getStatus() == null) {
            passengerDTO.setStatus(Status.ACTIVE);
        }

        // Save passenger
        Passenger passenger = modelMapper.map(passengerDTO, Passenger.class);
        passengerRepo.save(passenger);

        // Fetch saved passenger
        Passenger savedPassenger = passengerRepo.findById(passenger.getNic())
                .orElseThrow(() -> new RuntimeException("Failed to fetch saved passenger"));

        // Fetch schedule
        Schedule schedule = scheduleRepo.findById(bookingDTO.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (bookingDTO.getBookingStatus() == null) {
            bookingDTO.setBookingStatus(BookingStatus.PENDING);
        }
        if (bookingDTO.getPaymentStatus() == null) {
            bookingDTO.setPaymentStatus(PaymentStatus.UNPAID);
        }

        // ✅ Update available seat count
        int updated = scheduleRepo.updateAvailableSeats(
                bookingDTO.getScheduleId(),
                bookingDTO.getSeatsNumber()
        );

        if (updated == 0) {
            throw new RuntimeException("Not enough seats available for schedule: " + bookingDTO.getScheduleId());
        }

        // Save booking
        Booking booking = new Booking();
        booking.setPassenger(savedPassenger);
        booking.setSchedule(schedule);
        booking.setSeatsNumber(bookingDTO.getSeatsNumber());
        booking.setSeatPrice(bookingDTO.getSeatPrice());
        booking.setBookingStatus(bookingDTO.getBookingStatus());
        booking.setPaymentStatus(bookingDTO.getPaymentStatus());
        booking.setBookedAt(bookingDTO.getBookedAt());

        bookingRepo.save(booking);

        // ✅ Create QR content
        String qrMessage = """
        🚌 JourneyPass Booking Details 🧾

        Passenger NIC  : %s
        Name           : %s
        Schedule ID    : %s
        Booked At      : %s
        Seats Booked   : %d
        Price per Seat : %.2f
        Total Price    : %.2f
        Booking Status : %s
        Payment Status : %s
        """
                .formatted(
                        savedPassenger.getNic(),
                        savedPassenger.getPassengerName(),
                        schedule.getSchedule_id(),
                        bookingDTO.getBookedAt(),
                        bookingDTO.getSeatsNumber(),
                        bookingDTO.getSeatPrice(),
                        bookingDTO.getSeatPrice().multiply(BigDecimal.valueOf(bookingDTO.getSeatsNumber())),
                        bookingDTO.getBookingStatus(),
                        bookingDTO.getPaymentStatus()
                );

        // ✅ Generate QR code as a File
        File qrCodeFile = qrCodeGeneratorService.generateQRCode(qrMessage);

        // ✅ Send email with QR code to passenger
        try {
            String email = savedPassenger.getPassengerEmail();
            System.out.println("📩 Sending QR code to passenger: " + email);

            emailService.sendEmailWithAttachment(
                    email,
                    "JourneyPass Booking QR Code",
                    "Hi " + savedPassenger.getPassengerName() + ",\n\n" +
                            "Your booking was successful. Please find the QR code attached below.\n\n" +
                            "Thank you for choosing JourneyPass! 🚍",
                    qrCodeFile
            );
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with QR code", e);
        }
    }
}
