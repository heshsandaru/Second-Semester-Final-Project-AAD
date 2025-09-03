package lk.ijse.a1_journeypass_backend.repo;

import lk.ijse.a1_journeypass_backend.dto.BookingDTO;
import lk.ijse.a1_journeypass_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookedAtBetween(LocalDateTime start, LocalDateTime end);
}
