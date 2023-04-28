package com.example.imy_server.Repository.pulse;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Dto.Pulse.DailyPulseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PulseRepository extends JpaRepository<Pulse, Long> {

    @Query("SELECT AVG(p.pulseValue) FROM Pulse p WHERE p.pulseDate.createdDate = :date")
    String GetAvgPulseValueByDate(LocalDate date);

}
