package lk.ijse.a1_journeypass_backend.controller;

import lk.ijse.a1_journeypass_backend.dto.RouteDTO;
import lk.ijse.a1_journeypass_backend.service.impl.RouteServiceImpl;
import lk.ijse.a1_journeypass_backend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/JourneyPass/route")
public class RouteController {
    @Autowired
    private RouteServiceImpl routeService;

    @PostMapping("post")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUtil addRoute(@RequestBody RouteDTO routeDTO) {
        try {
            // Log the incoming request data for debugging
            System.out.println("Received RouteDTO: " + routeDTO);

            // Process the routeDTO
            boolean res = routeService.addRoute(routeDTO);
            if (res) {
                return new ResponseUtil(200, "Route Added Successfully", null);
            } else {
                return new ResponseUtil(400, "Route Added Failed", null);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return new ResponseUtil(500, "Something went wrong. Please try again later.", null);
        }
    }

    @GetMapping("getAll")
    public List<RouteDTO> getAllRoute() {
        routeService.getAllRoutes();
        return routeService.getAllRoutes();
    }

    @DeleteMapping("delete")
    public ResponseUtil deleteRoute(@RequestBody RouteDTO routeDTO) {
        boolean res = routeService.deleteRoute(routeDTO);
        if (res) {
            return new ResponseUtil(200,"Route Deleted Successfully",null);
        }else  {
            return new ResponseUtil(400,"Route Deleted Failed",null);
        }
    }

    @PutMapping("update")
    public ResponseUtil updateRoute(@RequestBody RouteDTO routeDTO) {
        boolean res = routeService.updateRoute(routeDTO);
        if (res) {
            return new ResponseUtil(200,"Route Updated Successfully",null);
        }else   {
            return new ResponseUtil(400,"Route Updated Failed",null);
        }
    }

    @GetMapping("search")
    public ResponseUtil searchRoute(@RequestParam String route_number) {
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setRoute_number(route_number);
        boolean exists = routeService.searchRoute(routeDTO);
        if (exists) {
            return new ResponseUtil(200, "Route Found", null);
        } else {
            return new ResponseUtil(200, "Route Not Found", null);
        }
    }

    @GetMapping("getRoutesWithStops")
    public List<RouteDTO> getRoutesWithStops() {
        routeService.getAllRoutesWithStops();
        return routeService.getAllRoutesWithStops();
    }

    @GetMapping("/numbers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<String> getAllRouteNumbers() {
        return routeService.findAllRouteNumbers();
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> countRoutes() {
        return ResponseEntity.ok(routeService.countRoutes());
    }

}
