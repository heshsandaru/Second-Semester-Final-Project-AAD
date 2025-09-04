package lk.ijse.a1_journeypass_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Bus {
    @Id
    private String bus_number;
    private String bus_name;
    private int total_seats;
    @Enumerated(EnumType.STRING)
    private BusType bus_type;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

    public Bus() {
    }

    public Bus(String bus_number, String bus_name, int total_seats, BusType bus_type) {
        this.bus_number = bus_number;
        this.bus_name = bus_name;
        this.total_seats = total_seats;
        this.bus_type = bus_type;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public int getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(int total_seats) {
        this.total_seats = total_seats;
    }

    public BusType getBus_type() {
        return bus_type;
    }

    public void setBus_type(BusType bus_type) {
        this.bus_type = bus_type;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "bus_number=" + bus_number +
                ", bus_name='" + bus_name + '\'' +
                ", total_seats=" + total_seats +
                ", bus_type=" + bus_type +
                '}';
    }
}