package lk.ijse.a1_journeypass_backend.dto;

public class BookingRequest {
    private PassengerDTO passengerDTO;
    private BookingDTO bookingDTO;

    public BookingRequest() {
    }

    public BookingRequest(PassengerDTO passengerDTO, BookingDTO bookingDTO) {
        this.passengerDTO = passengerDTO;
        this.bookingDTO = bookingDTO;
    }

    public BookingDTO getBookingDTO() {
        return bookingDTO;
    }

    public void setBookingDTO(BookingDTO bookingDTO) {
        this.bookingDTO = bookingDTO;
    }

    public PassengerDTO getPassengerDTO() {
        return passengerDTO;
    }

    public void setPassengerDTO(PassengerDTO passengerDTO) {
        this.passengerDTO = passengerDTO;
    }
}
