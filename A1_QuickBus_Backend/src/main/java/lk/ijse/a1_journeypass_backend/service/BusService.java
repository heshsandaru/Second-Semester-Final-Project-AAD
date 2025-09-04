package lk.ijse.a1_journeypass_backend.service;

import lk.ijse.a1_journeypass_backend.dto.BusDTO;

import java.util.List;

public interface BusService {
    boolean addBus(BusDTO busDTO);
    List<BusDTO> getAllBus();
    boolean deleteBus(BusDTO busDTO);
    boolean updateBus(BusDTO busDTO);
    boolean searchBus(BusDTO busDTO);
    List<String> findAllBusNumbers();
    long countBuses();
}
