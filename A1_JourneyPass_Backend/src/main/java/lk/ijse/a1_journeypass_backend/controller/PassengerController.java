package lk.ijse.a1_journeypass_backend.controller;

import lk.ijse.a1_journeypass_backend.dto.PassengerDTO;
import lk.ijse.a1_journeypass_backend.entity.Passenger;
import lk.ijse.a1_journeypass_backend.entity.Status;
import lk.ijse.a1_journeypass_backend.service.impl.PassengerServiceImpl;
import lk.ijse.a1_journeypass_backend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:63342")
@RestController
@RequestMapping("api/v1/JourneyPass/passenger")
public class PassengerController {
    @Autowired
    private PassengerServiceImpl passengerService;

    @PostMapping("/post")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUtil postPassenger(@RequestBody PassengerDTO passengerDTO) {
        boolean res = passengerService.addPassenger(passengerDTO);
        if (res){
            return new ResponseUtil(201,"Passenger Saved Successfully.",null);
        }else {
            return new ResponseUtil(200,"Passenger Saving Failed.",null);
        }
    }

    @GetMapping("/getAll")
    public List<PassengerDTO> getAllPassengers() {
        passengerService.getAllPassengers();
        return passengerService.getAllPassengers();
    }

    @PutMapping("/updateStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUtil updatePassengerStatus(@RequestParam String nic, @RequestParam Status status) {
        try {
            passengerService.updatePassengerStatus(nic, status);
            return new ResponseUtil(200, "Passenger status updated successfully.", null);
        } catch (RuntimeException e) {
            return new ResponseUtil(404, "Passenger not found.", null);
        }
    }
}
