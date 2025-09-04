package lk.ijse.a1_journeypass_backend.controller;

import lk.ijse.a1_journeypass_backend.dto.ScheduleDTO;
import lk.ijse.a1_journeypass_backend.dto.ScheduleDetailsDTO;
import lk.ijse.a1_journeypass_backend.dto.ScheduleRouteDetailsDTO;
import lk.ijse.a1_journeypass_backend.service.impl.ScheduleServiceImpl;
import lk.ijse.a1_journeypass_backend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/JourneyPass/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleServiceImpl scheduleService;

    @PostMapping("post")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUtil createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        System.out.println("Received Data: " + scheduleDTO); // Debugging
        boolean res = scheduleService.createSchedule(scheduleDTO);
        if (res) {
            return new ResponseUtil(200, "Schedule Created", null);
        }else {
            return new ResponseUtil(200, "Schedule Not Created", null);
        }
    }

    @GetMapping("getAll")
    public List<ScheduleDTO> getAllSchedule() {
        scheduleService.getAllSchedules();
        return scheduleService.getAllSchedules();
    }

    @DeleteMapping("delete")
    public ResponseUtil deleteSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        boolean res = scheduleService.deleteSchedule(scheduleDTO);
        if (res) {
            return new ResponseUtil(200, "Schedule Deleted", null);
        }else  {
            return new ResponseUtil(200, "Schedule Not Deleted", null);
        }
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUtil updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        boolean res = scheduleService.updateSchedule(scheduleDTO);
        if (res) {
            return new ResponseUtil(200, "Schedule Updated", null);
        }else  {
            return new ResponseUtil(200, "Schedule Not Updated", null);
        }
    }

    @GetMapping("search1")
    public ResponseUtil searchSchedule(@RequestParam String schedule_id){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setSchedule_id(schedule_id);

        boolean res = scheduleService.searchSchedule(scheduleDTO);
        if (res) {
            return new ResponseUtil(200, "Schedule Found", null);
        }else  {
            return new ResponseUtil(200, "Schedule Not Found", null);
        }
    }

    @GetMapping("/search2")
    public List<ScheduleDetailsDTO> searchSchedules(@RequestParam String departure, @RequestParam String destination) {
        return scheduleService.getSchedulesByDepartureAndDestination(departure, destination);
    }

    @GetMapping("/search3")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<ScheduleRouteDetailsDTO> findSchedulesByRoute(@RequestParam String departure, @RequestParam String destination) {
        return scheduleService.findSchedulesByRoute(departure, destination);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getSchedulesCount() {
        return ResponseEntity.ok(scheduleService.getSchedulesCount());
    }
}


