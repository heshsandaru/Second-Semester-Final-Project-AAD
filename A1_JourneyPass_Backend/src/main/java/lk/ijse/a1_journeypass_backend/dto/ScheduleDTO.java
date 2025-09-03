package lk.ijse.a1_journeypass_backend.dto;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduleDTO {
    private String schedule_id;
    private String bus_number;
    private String route_number;
    private Time departure_time;
    private Time arrival_time;
    private BigDecimal price;
    private int available_seats;

    public ScheduleDTO() {
    }

    public ScheduleDTO(String schedule_id, String bus_number, String route_number, Time departure_time, Time arrival_time, BigDecimal price, int available_seats) {
        this.schedule_id = schedule_id;
        this.bus_number = bus_number;
        this.route_number = route_number;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.price = price;
        this.available_seats = available_seats;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public String getRoute_number() {
        return route_number;
    }

    public void setRoute_number(String route_number) {
        this.route_number = route_number;
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

    @Override
    public String toString() {
        return "ScheduleDTO{" +
                "schedule_id=" + schedule_id +
                ", bus_number='" + bus_number + '\'' +
                ", route_number='" + route_number + '\'' +
                ", departure_time=" + departure_time +
                ", arrival_time=" + arrival_time +
                ", price=" + price +
                ", available_seats=" + available_seats +
                '}';
    }
}
