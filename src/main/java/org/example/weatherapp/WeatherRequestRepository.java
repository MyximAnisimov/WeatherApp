package org.example.weatherapp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRequestRepository extends JpaRepository<WeatherRequest, Long> {
}

