package lk.ijse.a1_journeypass_backend.service.impl;

import lk.ijse.a1_journeypass_backend.dto.RouteDTO;
import lk.ijse.a1_journeypass_backend.entity.Route;
import lk.ijse.a1_journeypass_backend.repo.RouteRepo;
import lk.ijse.a1_journeypass_backend.service.RouteService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteRepo routeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean addRoute(RouteDTO routeDTO) {
        if (routeRepo.existsById(routeDTO.getRoute_number())) {
            System.out.println("Route already exists: " + routeDTO.getRoute_number());
            return false;
        }
        Route route = modelMapper.map(routeDTO, Route.class);
        System.out.println("Saving route: " + routeDTO.getRoute_number());

        routeRepo.save(route);

        if (routeRepo.existsById(routeDTO.getRoute_number())) {
            System.out.println("Route already exists: " + routeDTO.getRoute_number());
        }else {
            System.out.println("Failed to save route: " + routeDTO.getRoute_number());
        }

        return true;
    }

    @Override
    public List<RouteDTO> getAllRoutes() {
        return modelMapper.map(routeRepo.findAll(),
                new TypeToken<List<RouteDTO>>() {}.getType());
    }

    @Override
    public boolean deleteRoute(RouteDTO routeDTO) {
        routeRepo.deleteById(routeDTO.getRoute_number());
        return true;
    }

    @Override
    public boolean updateRoute(RouteDTO routeDTO) {
        if (routeRepo.existsById(routeDTO.getRoute_number())) {
            routeRepo.save(modelMapper.map(routeDTO, Route.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean searchRoute(RouteDTO routeDTO) {
        return routeRepo.findById(routeDTO.getRoute_number()).isPresent();
    }

    @Override
    public List<RouteDTO> getAllRoutesWithStops() {
        List<Object[]> routes = routeRepo.getRoutesWithStops();
        List<RouteDTO> routeDTOs = new ArrayList<>();

        for (Object[] row : routes) {
            RouteDTO dto = new RouteDTO();
            dto.setRoute_number((String) row[0]);
            dto.setDeparture((String) row[1]);
            dto.setDestination((String) row[2]);

            // Handle null for stops
            if (row[3] != null) {
                dto.setStops(List.of(((String) row[3]).split(", "))); // Convert to List
            } else {
                dto.setStops(new ArrayList<>()); // Set empty list if no stops
            }

            dto.setDistance(((Number) row[4]).intValue());
            dto.setEstimated_time((Time) row[5]);

            routeDTOs.add(dto);
        }

        return routeDTOs;
    }

    @Override
    public List<String> findAllRouteNumbers() {
        return routeRepo.findAllRouteNumbers();
    }

    @Override
    public long countRoutes() {
        return routeRepo.count();
    }
}
