package lk.ijse.a1_journeypass_backend.dto;

import lk.ijse.a1_journeypass_backend.entity.BusType;

public class BusDTO {
    private String bus_number;
    private String bus_name;
    private int total_seats;
    private BusType bus_type;

    public BusDTO() {
    }

    public BusDTO(String bus_number, String bus_name, int total_seats,BusType bus_type) {
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

    @Override
    public String toString() {
        return "BusDTO{" +
                "bus_number=" + bus_number +
                ", bus_name='" + bus_name + '\'' +
                ", total_seats=" + total_seats +
                ", bus_type=" + bus_type +
                '}';
    }
}
