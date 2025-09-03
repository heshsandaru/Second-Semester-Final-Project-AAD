package lk.ijse.a1_journeypass_backend.service;

import lk.ijse.a1_journeypass_backend.dto.BookingDTO;
import lk.ijse.a1_journeypass_backend.dto.PassengerDTO;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    boolean addBooking(BookingDTO bookingDTO);
    List<BookingDTO> getAllBookings();
    List<BookingDTO> getBookingsByDate(LocalDate date);
    void savePassengerAndBooking(PassengerDTO passengerDTO, BookingDTO bookingDTO);
}
