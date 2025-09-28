package lk.ijse.a1_journeypass_backend.repo;

import lk.ijse.a1_journeypass_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookedAtBetween(LocalDateTime start, LocalDateTime end);


    @Query("SELECT b.bookedSeatsNumber FROM Booking b WHERE b.schedule.schedule_id = :scheduleId")
    List<String> findSeatNumbersByScheduleId(@Param("scheduleId") String scheduleId);

}
