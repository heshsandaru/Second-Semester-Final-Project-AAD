package lk.ijse.a1_journeypass_backend.repo;

import lk.ijse.a1_journeypass_backend.dto.RouteDTO;
import lk.ijse.a1_journeypass_backend.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteRepo extends JpaRepository<Route, String> {

    @Query(value = """
        SELECT r.route_number, r.departure, r.destination, 
               GROUP_CONCAT(rs.stop ORDER BY rs.stop SEPARATOR ', ') AS stops, 
               r.distance, r.estimated_time
        FROM route r
        LEFT JOIN route_stops rs ON r.route_number = rs.route_number
        GROUP BY r.route_number, r.departure, r.destination, r.distance, r.estimated_time
        """, nativeQuery = true)
    List<Object[]> getRoutesWithStops();

    @Query(value = "SELECT r.route_number FROM Route r",nativeQuery = true)
    List<String>findAllRouteNumbers();

    @Query(value = "SELECT COUNT(r) FROM Route r",nativeQuery = true)
    long countRoutes();
}