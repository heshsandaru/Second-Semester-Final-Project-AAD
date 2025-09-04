package lk.ijse.a1_journeypass_backend.entity;

import jakarta.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
public class Route {

    @Id
    private String route_number;

    private String departure;
    private String destination;

    @ElementCollection
    @CollectionTable(name = "route_stops", joinColumns = @JoinColumn(name = "route_number"))
    @Column(name = "stop")
    private List<String> stops;

    private int distance;

    private Time estimated_time;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

    public Route() {
    }

    public Route(String route_number, String departure, String destination, List<String> stops, int distance, Time estimated_time, List<Schedule> schedules) {
        this.route_number = route_number;
        this.departure = departure;
        this.destination = destination;
        this.stops = stops;
        this.distance = distance;
        this.estimated_time = estimated_time;
        this.schedules = schedules;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
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
}