package lk.ijse.a1_journeypass_backend.service.impl;

import lk.ijse.a1_journeypass_backend.dto.ScheduleDTO;
import lk.ijse.a1_journeypass_backend.dto.ScheduleRouteDetailsDTO;
import lk.ijse.a1_journeypass_backend.entity.BusType;
import lk.ijse.a1_journeypass_backend.entity.Schedule;
import lk.ijse.a1_journeypass_backend.dto.ScheduleDetailsDTO;
import lk.ijse.a1_journeypass_backend.repo.ScheduleRepo;
import lk.ijse.a1_journeypass_backend.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean createSchedule(ScheduleDTO scheduleDTO) {
        if (scheduleRepo.existsById(scheduleDTO.getSchedule_id())){
            System.out.println("Schedule already exists" + scheduleDTO.getSchedule_id());
            return false;
        }
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        System.out.println("Creating Schedule: " + scheduleDTO.getSchedule_id());

        scheduleRepo.save(schedule);

        if (scheduleRepo.existsById(scheduleDTO.getSchedule_id())){
            System.out.println("Schedule already exists" + scheduleDTO.getSchedule_id());
        }else {
            System.out.println("Failed To creating Schedule: " + scheduleDTO.getSchedule_id());
        }

        return true;
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        return modelMapper.map(scheduleRepo.findAll(),
                new TypeToken<List<ScheduleDTO>>() {}.getType());
    }

    @Override
    public boolean deleteSchedule(ScheduleDTO scheduleDTO) {
        scheduleRepo.deleteById(scheduleDTO.getSchedule_id());
        return true;
    }

    @Override
    public boolean updateSchedule(ScheduleDTO scheduleDTO) {
        if (scheduleRepo.existsById(scheduleDTO.getSchedule_id())){
            scheduleRepo.save(modelMapper.map(scheduleDTO, Schedule.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean searchSchedule(ScheduleDTO scheduleDTO) {
        return scheduleRepo.findById(scheduleDTO.getSchedule_id()).isPresent();
    }

    @Override
    public List<ScheduleDetailsDTO> getSchedulesByDepartureAndDestination(String departure, String destination) {
        List<Object[]> results = scheduleRepo.findSchedulesByDepartureAndDestination(departure, destination);

        return results.stream().map(obj -> {
//            System.out.println("Raw Data: " + Arrays.toString(obj));

            Time departureTime = null;
            Time arrivalTime = null;

            if (obj[0] instanceof Time time) {
                departureTime = Time.valueOf(time.toLocalTime());
            }
            if (obj[1] instanceof Time time) {
                arrivalTime = Time.valueOf(time.toLocalTime());
            }

            return new ScheduleDetailsDTO(
                    departureTime,
                    arrivalTime,
                    (String) obj[2],
                    obj[3] != null ? BusType.valueOf((String) obj[3]) : null,
                    (String) obj[4]
            );
        }).toList();
    }

    @Override
    public List<ScheduleRouteDetailsDTO> findSchedulesByRoute(String departure, String destination) {
        List<Object[]> results = scheduleRepo.findSchedulesByRoute(departure, destination);

        return results.stream().map(obj -> {
            Time departureTime = null;
            Time arrivalTime = null;
            String estimatedTime = null; // Change type to String

            if (obj[4] instanceof Time time) {
                departureTime = Time.valueOf(time.toLocalTime());
            }
            if (obj[5] instanceof Time time) {
                arrivalTime = Time.valueOf(time.toLocalTime());
            }
            if (obj[6] != null) {
                estimatedTime = obj[6].toString();  // Ensure it converts properly
            }

            System.out.println("Departure Time: " + departureTime);
            System.out.println("Arrival Time: " + arrivalTime);
            System.out.println("Estimated Time: " + estimatedTime);

            return new ScheduleRouteDetailsDTO(
                    (String) obj[0],
                    (String) obj[1],
                    (String) obj[2],
                    (String) obj[3],
                    departureTime,
                    arrivalTime,
                    Time.valueOf(estimatedTime), // Now a string
                    (String) obj[7],
                    (String) obj[8],
                    obj[9] != null ? BusType.valueOf((String) obj[9]).toString() : "UNKNOWN",
                    obj[10] instanceof BigDecimal ? (BigDecimal) obj[10] : new BigDecimal(obj[10].toString()),
                    obj[11] != null ? (Integer) obj[11] : 0
            );
        }).toList();
    }

    @Override
    public long getSchedulesCount() {
        return scheduleRepo.count();
    }

    @Override
    @Transactional
    public int updateAvailableSeats(String scheduleId, int seatsToBook) {
        return scheduleRepo.updateAvailableSeats(scheduleId, seatsToBook);
    }
}
