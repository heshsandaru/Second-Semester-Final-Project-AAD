package lk.ijse.a1_journeypass_backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Schedule {
    @Id
    private String schedule_id;

    @ManyToOne
    @JoinColumn(name = "bus_number", nullable = false)
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "route_number", nullable = false)
    private Route route;

    private Time departure_time;
    private Time arrival_time;
    private BigDecimal price;
    private int available_seats;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    public Schedule() {
    }

    public Schedule(String schedule_id, Bus bus, Route route, Time departure_time, Time arrival_time, BigDecimal price, int available_seats, List<Booking> bookings) {
        this.schedule_id = schedule_id;
        this.bus = bus;
        this.route = route;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.price = price;
        this.available_seats = available_seats;
        this.bookings = bookings;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Time getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Time departure_time) {
        this.departure_time = departure_time;
    }

    public Time getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(Time arrival_time) {
        this.arrival_time = arrival_time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(int available_seats) {
        this.available_seats = available_seats;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "schedule_id=" + schedule_id +
                ", bus=" + bus +
                ", route=" + route +
                ", departure_time=" + departure_time +
                ", arrival_time=" + arrival_time +
                ", price=" + price +
                ", available_seats=" + available_seats +
                '}';
    }
}
