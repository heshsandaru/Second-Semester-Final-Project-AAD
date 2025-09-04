package lk.ijse.a1_journeypass_backend.service;

import lk.ijse.a1_journeypass_backend.dto.PassengerDTO;
import lk.ijse.a1_journeypass_backend.entity.Status;

import java.util.List;

public interface PassengerService {
    boolean addPassenger(PassengerDTO passengerDTO);
    List<PassengerDTO> getAllPassengers();
    void updatePassengerStatus(String nic, Status status);
}
