package lk.ijse.a1_journeypass_backend.repo;

import lk.ijse.a1_journeypass_backend.entity.Passenger;
import lk.ijse.a1_journeypass_backend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface PassengerRepo extends JpaRepository<Passenger, String> {
    Passenger findByPassengerName(String passengerName);

    @Transactional
    @Modifying
    @Query("UPDATE Passenger p SET p.status = :status WHERE p.nic = :nic")
    void updatePassengerStatus(@Param("nic") String nic, @Param("status") Status status);
}
