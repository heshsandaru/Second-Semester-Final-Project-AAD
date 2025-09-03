package lk.ijse.a1_journeypass_backend.controller;

import lk.ijse.a1_journeypass_backend.dto.BookingDTO;
import lk.ijse.a1_journeypass_backend.dto.BookingRequest;
import lk.ijse.a1_journeypass_backend.dto.PassengerDTO;
import lk.ijse.a1_journeypass_backend.entity.Booking;
import lk.ijse.a1_journeypass_backend.service.BookingService;
import lk.ijse.a1_journeypass_backend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/JourneyPass/booking")
public class BookingController {
    @Autowired
    public BookingService bookingService;

    @PostMapping("/post")
    public ResponseUtil addBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            System.out.println("Received request to add booking: " + bookingDTO);

            boolean res = bookingService.addBooking(bookingDTO);
            if (res) {
                return new ResponseUtil(200,"Booking Completed.",null);
            }else {
                return new ResponseUtil(400,"Booking Failed",null);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtil(500,"Something went wrong. Please try again later.",null);
        }
    }

    @GetMapping("/getAll")
    public List<BookingDTO> getAllBookings() {
        bookingService.getAllBookings();
        return bookingService.getAllBookings();
    }

    @GetMapping("/searchByDate")
    public List<BookingDTO> searchBookingsByDate(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return bookingService.getBookingsByDate(localDate);
    }

    @PostMapping("/saveFullBooking")
    public ResponseUtil savePassengerAndBooking(@RequestBody BookingRequest bookingRequest) {
        try {
            bookingService.savePassengerAndBooking(bookingRequest.getPassengerDTO(), bookingRequest.getBookingDTO());
            return new ResponseUtil(200, "Passenger and Booking saved successfully", null);
        } catch (Exception e) {
            return new ResponseUtil(500, "Error: " + e.getMessage(), null);
        }
    }
}
