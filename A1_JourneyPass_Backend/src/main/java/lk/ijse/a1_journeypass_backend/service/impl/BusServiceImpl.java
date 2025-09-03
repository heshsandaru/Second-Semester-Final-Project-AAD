package lk.ijse.a1_journeypass_backend.service.impl;

import lk.ijse.a1_journeypass_backend.dto.BusDTO;
import lk.ijse.a1_journeypass_backend.entity.Bus;
import lk.ijse.a1_journeypass_backend.repo.BusRepo;
import lk.ijse.a1_journeypass_backend.service.BusService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusRepo busRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean addBus(BusDTO busDTO) {
        if (busRepo.existsById(busDTO.getBus_number())) {
            System.out.println("Bus already exists: " + busDTO.getBus_number());
            return false;
        }

        Bus bus = modelMapper.map(busDTO, Bus.class);
        System.out.println("Saving bus: " + bus);

        busRepo.save(bus);

        // Verify if the bus was saved
        if (busRepo.existsById(busDTO.getBus_number())) {
            System.out.println("Bus saved successfully: " + busDTO.getBus_number());
        } else {
            System.out.println("Failed to save bus!");
        }
        return true;
    }

    @Override
    public List<BusDTO> getAllBus() {
        return modelMapper.map(busRepo.findAll(),
                new TypeToken<List<BusDTO>>() {}.getType());

    }

    @Override
    public boolean deleteBus(BusDTO busDTO) {
        busRepo.deleteById(busDTO.getBus_number());
        return true;
    }

    @Override
    public boolean updateBus(BusDTO busDTO) {
        if (busRepo.existsById(busDTO.getBus_number())) {
            busRepo.save(modelMapper.map(busDTO, Bus.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean searchBus(BusDTO busDTO) {
        return busRepo.findById(busDTO.getBus_number()).isPresent();
    }

    @Override
    public List<String> findAllBusNumbers() {
        return busRepo.findAllBusNumbers();
    }

    @Override
    public long countBuses() {
        return busRepo.count();
    }
}
