package lk.ijse.a1_journeypass_backend.dto;

import lk.ijse.a1_journeypass_backend.entity.BusType;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduleDetailsDTO {
    private Time departure_time;
    private Time arrival_time;
    private String route_number;
    private BusType bus_type;
    private String bus_number;

    public ScheduleDetailsDTO() {
    }

    public ScheduleDetailsDTO(Time departure_time, Time arrival_time, String route_number, BusType bus_type, String bus_number) {
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.route_number = route_number;
        this.bus_type = bus_type;
        this.bus_number = bus_number;
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

    public String getRoute_number() {
        return route_number;
    }

    public void setRoute_number(String route_number) {
        this.route_number = route_number;
    }

    public BusType getBus_type() {
        return bus_type;
    }

    public void setBus_type(BusType bus_type) {
        this.bus_type = bus_type;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    @Override
    public String toString() {
        return "ScheduleDetailsDTO{" +
                "departure_time=" + departure_time +
                ", arrival_time=" + arrival_time +
                ", route_number='" + route_number + '\'' +
                ", bus_type=" + bus_type +
                ", bus_number='" + bus_number + '\'' +
                '}';
    }
}
