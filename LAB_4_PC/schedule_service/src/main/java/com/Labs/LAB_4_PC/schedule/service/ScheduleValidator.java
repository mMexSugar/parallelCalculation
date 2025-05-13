package com.Labs.LAB_4_PC.schedule.service;

import com.Labs.LAB_4_PC.schedule.entity.Schedule;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;


@Component
public class ScheduleValidator {

    private final RestTemplate restTemplate;

    public ScheduleValidator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validate(Schedule schedule) {
        if (schedule.getRouteId() == null || schedule.getRouteId().isEmpty()) {
            throw new IllegalArgumentException("Route ID не може бути порожнім");
        }

        if (!isValidTimeInterval(schedule.getStartTime(), schedule.getEndTime())) {
            throw new IllegalArgumentException("Невірний формат або логіка часу: startTime має бути раніше за endTime");
        }

        if (schedule.getFrequencyMinutes() <= 0) {
            throw new IllegalArgumentException("Частота має бути додатним числом хвилин");
        }

        try {
            String url = "http://localhost:8080/routes/" + schedule.getRouteId(); // через Gateway
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Маршрут з ID " + schedule.getRouteId() + " не знайдено");
        }
    }

    private boolean isValidTimeInterval(LocalTime start, LocalTime end) {
        return start != null && end != null && start.isBefore(end);
    }

    private boolean isValidTimeInterval(String start, String end) {
        try {
            LocalTime startTime = LocalTime.parse(start);
            LocalTime endTime = LocalTime.parse(end);
            return startTime.isBefore(endTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
