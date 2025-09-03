package lk.ijse.a1_journeypass_backend.repo;

import lk.ijse.a1_journeypass_backend.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BusRepo extends JpaRepository<Bus,String> {
    @Query(value = "SELECT b.bus_number FROM Bus b",nativeQuery = true)
    List<String> findAllBusNumbers();

    @Query(value = "SELECT COUNT(b) FROM Bus b",nativeQuery = true)
    long countBuses();
}
