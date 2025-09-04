package lk.ijse.a1_journeypass_backend.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Passenger implements Serializable {
    @Id
    @Column(name = "nic", nullable = false)
    private String nic;

    public String passengerName;
    public int passengerMobile;
    public String passengerEmail;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Status status = Status.ACTIVE;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;


    public Passenger() {
    }

    public Passenger(String nic, String passengerName, int passengerMobile, String passengerEmail, Status status, List<Booking> bookings) {
        this.nic = nic;
        this.passengerName = passengerName;
        this.passengerMobile = passengerMobile;
        this.passengerEmail = passengerEmail;
        this.status = status;
        this.bookings = bookings;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getPassengerMobile() {
        return passengerMobile;
    }

    public void setPassengerMobile(int passengerMobile) {
        this.passengerMobile = passengerMobile;
    }

    public String getNic() {
        return nic;
    }


    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                ", passengerName='" + passengerName + '\'' +
                ", passengerMobile=" + passengerMobile +
                ", nic='" + nic + '\'' +
                ", passengerEmail='" + passengerEmail + '\'' +
                ", status=" + status +
                '}';
    }
}
