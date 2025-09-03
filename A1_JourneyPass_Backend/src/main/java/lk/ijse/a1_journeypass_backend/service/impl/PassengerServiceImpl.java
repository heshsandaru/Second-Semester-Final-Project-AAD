package lk.ijse.a1_journeypass_backend.service.impl;

import lk.ijse.a1_journeypass_backend.dto.PassengerDTO;
import lk.ijse.a1_journeypass_backend.entity.Passenger;
import lk.ijse.a1_journeypass_backend.entity.Status;
import lk.ijse.a1_journeypass_backend.repo.PassengerRepo;
import lk.ijse.a1_journeypass_backend.service.PassengerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {
    @Autowired
    private PassengerRepo passengerRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean addPassenger(PassengerDTO passengerDTO) {
        if (passengerDTO.getStatus() == null) {
            passengerDTO.setStatus(Status.ACTIVE);
        }

        if (passengerRepo.existsById(passengerDTO.getNic())) {
            System.out.println("Passenger already exists : " + passengerDTO.getNic());
            return false;
        }

        Passenger passenger = modelMapper.map(passengerDTO, Passenger.class);
        passenger.setNic(passengerDTO.getNic());
        passengerRepo.save(passenger);
        return true;
    }

    @Override
    public List<PassengerDTO> getAllPassengers() {
        return modelMapper.map(passengerRepo.findAll(),
                new TypeToken<List<PassengerDTO>>() {}.getType());

    }

    @Override
    public void updatePassengerStatus(String nic, Status status) {
        Optional<Passenger> optionalPassenger = passengerRepo.findById(nic);
        if (optionalPassenger.isPresent()) {
            Passenger passenger = optionalPassenger.get();
            passenger.setStatus(status);
            passengerRepo.save(passenger);
        } else {
            throw new RuntimeException("Passenger not found with NIC: " + nic);
        }
    }
}
