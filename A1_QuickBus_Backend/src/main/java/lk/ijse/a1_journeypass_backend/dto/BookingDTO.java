package lk.ijse.a1_journeypass_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lk.ijse.a1_journeypass_backend.entity.BookingStatus;
import lk.ijse.a1_journeypass_backend.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingDTO {
    private Long bookingId;
    private String passengerId;
    private String scheduleId;
    private int seatsNumber;
    private BigDecimal seatPrice;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime bookedAt = LocalDateTime.now();

    public BookingDTO() {
    }

    public BookingDTO(Long bookingId, String passengerId, String scheduleId, int seatsNumber, BigDecimal seatPrice, BookingStatus bookingStatus, PaymentStatus paymentStatus, LocalDateTime bookedAt) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.scheduleId = scheduleId;
        this.seatsNumber = seatsNumber;
        this.seatPrice = seatPrice;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.bookedAt = bookedAt;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingId=" + bookingId +
                ", passengerId='" + passengerId + '\'' +
                ", scheduleId='" + scheduleId + '\'' +
                ", seatsNumber=" + seatsNumber +
                ", seatPrice=" + seatPrice +
                ", bookingStatus=" + bookingStatus +
                ", paymentStatus=" + paymentStatus +
                ", bookedAt=" + bookedAt +
                '}';
    }
}
