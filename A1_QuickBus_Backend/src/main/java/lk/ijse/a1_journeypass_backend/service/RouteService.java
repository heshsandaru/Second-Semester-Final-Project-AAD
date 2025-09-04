package lk.ijse.a1_journeypass_backend.service;

import lk.ijse.a1_journeypass_backend.dto.RouteDTO;

import java.util.List;

public interface RouteService {
    boolean addRoute(RouteDTO routeDTO);
    List<RouteDTO> getAllRoutes();
    boolean deleteRoute(RouteDTO routeDTO);
    boolean updateRoute(RouteDTO routeDTO);
    boolean searchRoute(RouteDTO routeDTO);
    List<RouteDTO>getAllRoutesWithStops();
    List<String>findAllRouteNumbers();
    long countRoutes();
}
