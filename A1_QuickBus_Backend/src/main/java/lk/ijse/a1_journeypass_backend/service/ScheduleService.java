package lk.ijse.a1_journeypass_backend.service;

import lk.ijse.a1_journeypass_backend.dto.ScheduleDTO;
import lk.ijse.a1_journeypass_backend.dto.ScheduleDetailsDTO;
import lk.ijse.a1_journeypass_backend.dto.ScheduleRouteDetailsDTO;

import java.util.List;

public interface ScheduleService{
    boolean createSchedule(ScheduleDTO scheduleDTO);
    List<ScheduleDTO> getAllSchedules();
    boolean deleteSchedule(ScheduleDTO scheduleDTO);
    boolean updateSchedule(ScheduleDTO scheduleDTO);
    boolean searchSchedule(ScheduleDTO scheduleDTO);
    List<ScheduleDetailsDTO> getSchedulesByDepartureAndDestination(String departure, String destination);
    List<ScheduleRouteDetailsDTO> findSchedulesByRoute(String departure, String destination);
    long getSchedulesCount();
    int updateAvailableSeats(String scheduleId, int seatsToBook);
}
