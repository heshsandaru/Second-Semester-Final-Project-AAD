package lk.ijse.a1_journeypass_backend.dto;

import java.sql.Time;
import java.util.List;

public class RouteDTO {
    private String route_number;
    private String departure;
    private String destination;
    private List<String> stops;
    private int distance;
    private Time estimated_time;

    public RouteDTO() {
    }

    public RouteDTO(String route_number, String departure, String destination, List<String> stops, int distance, Time estimated_time) {
        this.route_number = route_number;
        this.departure = departure;
        this.destination = destination;
        this.stops = stops;
        this.distance = distance;
        this.estimated_time = estimated_time;
    }

    public String getRoute_number() {
        return route_number;
    }

    public void setRoute_number(String route_number) {
        this.route_number = route_number;
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

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Time getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(Time estimated_time) {
        this.estimated_time = estimated_time;
    }

    @Override
    public String toString() {
        return "RouteDTO{" +
                "route_number='" + route_number + '\'' +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", stops=" + stops +
                ", distance=" + distance +
                ", estimated_time=" + estimated_time +
                '}';
    }
}