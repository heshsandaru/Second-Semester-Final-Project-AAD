package lk.ijse.a1_journeypass_backend.repo;

import lk.ijse.a1_journeypass_backend.dto.ScheduleRouteDetailsDTO;
import lk.ijse.a1_journeypass_backend.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScheduleRepo extends JpaRepository<Schedule,String> {

    @Query(value = "SELECT s.departure_time, s.arrival_time, r.route_number, b.bus_type, b.bus_number " +
            "FROM schedule s " +
            "INNER JOIN route r ON s.route_number = r.route_number " +
            "INNER JOIN bus b ON s.bus_number = b.bus_number " +
            "WHERE r.departure = :departure AND r.destination = :destination",
            nativeQuery = true)
    List<Object[]> findSchedulesByDepartureAndDestination(@Param("departure") String departure, @Param("destination") String destination);

    @Query(value = "SELECT r.route_number, r.departure," +
            " r.destination, s.schedule_id, s.departure_time, s.arrival_time, " +
            "r.estimated_time," +
            "b.bus_number, b.bus_name, b.bus_type, s.price, s.available_seats " +
            "FROM schedule s JOIN bus b ON s.bus_number = b.bus_number " +
            "JOIN route r ON s.route_number = r.route_number " +
            "WHERE r.departure = :departure AND r.destination = :destination",
            nativeQuery = true)
    List<Object[]> findSchedulesByRoute(@Param("departure") String departure, @Param("destination") String destination);

    @Query(value = "SELECT COUNT(s) FROM Schedule s",nativeQuery = true)
    long countSchedules();

    @Modifying
    @Transactional
    @Query(value = "UPDATE schedule SET available_seats = available_seats - :seatsToBook " +
            "WHERE schedule_id = :scheduleId AND available_seats >= :seatsToBook", nativeQuery = true)
    int updateAvailableSeats(@Param("scheduleId") String scheduleId, @Param("seatsToBook") int seatsToBook);
}