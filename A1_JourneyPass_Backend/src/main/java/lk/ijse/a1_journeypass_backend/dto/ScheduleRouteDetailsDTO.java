package lk.ijse.a1_journeypass_backend.dto;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;

public class ScheduleRouteDetailsDTO {
    private String routeNumber;
    private String departure;
    private String destination;
    private String scheduleId;
    private Time departureTime;
    private Time arrivalTime;
    private Time estimatedTime;
    private String busNumber;
    private String busName;
    private String busType;
    private BigDecimal price;
    private int availableSeats;

    public ScheduleRouteDetailsDTO() {
    }

    public ScheduleRouteDetailsDTO(String routeNumber, String departure, String destination, String scheduleId, Time departureTime, Time arrivalTime, Time estimatedTime, String busNumber, String busName, String busType, BigDecimal price, int availableSeats) {
        this.routeNumber = routeNumber;
        this.departure = departure;
        this.destination = destination;
        this.scheduleId = scheduleId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.estimatedTime = estimatedTime;
        this.busNumber = busNumber;
        this.busName = busName;
        this.busType = busType;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "ScheduleRouteDetailsDTO{" +
                "routeNumber='" + routeNumber + '\'' +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", scheduleId='" + scheduleId + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", estimatedTime='" + estimatedTime + '\'' +
                ", busNumber='" + busNumber + '\'' +
                ", busName='" + busName + '\'' +
                ", busType='" + busType + '\'' +
                ", price=" + price +
                ", availableSeats=" + availableSeats +
                '}';
    }
}
