package lk.ijse.a1_journeypass_backend.controller;

import lk.ijse.a1_journeypass_backend.dto.BusDTO;
import lk.ijse.a1_journeypass_backend.repo.BusRepo;
import lk.ijse.a1_journeypass_backend.service.impl.BusServiceImpl;
import lk.ijse.a1_journeypass_backend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:63342")
@RestController
@RequestMapping("api/v1/journeyPass/bus")
public class BusController {
    @Autowired
    private BusServiceImpl busService;

    @PostMapping("post")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUtil saveBus(@RequestBody BusDTO busDTO) {
        boolean res = busService.addBus(busDTO);
        if (res){
            return new ResponseUtil(201,"Bus Saved Successfully",null);
        }else  {
            return new ResponseUtil(200,"Bus Save Failed",null);
        }
    }

    @GetMapping("getAll")
    public List<BusDTO> getAllBus() {
        busService.getAllBus();
        return busService.getAllBus();
    }

    @DeleteMapping("delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUtil deleteBus(@RequestBody BusDTO busDTO) {
        boolean res = busService.deleteBus(busDTO);
        if (res){
            return new ResponseUtil(201,"Bus Delete Successfully",null);
        }else  {
            return new ResponseUtil(200,"Bus Delete Failed",null);
        }
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUtil updateBus(@RequestBody BusDTO busDTO) {
        boolean res = busService.updateBus(busDTO);
        if (res){
            return new ResponseUtil(201,"Bus Update Successfully",null);
        }else {
            return new ResponseUtil(200,"Bus Update Failed",null);
        }
    }

    @GetMapping("search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUtil searchBus(@RequestParam String bus_number) {
        BusDTO busDTO = new BusDTO();
        busDTO.setBus_number(bus_number);
        boolean res = busService.searchBus(busDTO);
        if (res){
            return new ResponseUtil(200,"Bus Found",null);
        }else  {
            return new ResponseUtil(200,"Bus Not Found",null);
        }
    }

    @GetMapping("/numbers")
    public List<String> getAllBusNumbers() {
        return busService.findAllBusNumbers();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getBusCount() {
        return ResponseEntity.ok(busService.countBuses());
    }
}