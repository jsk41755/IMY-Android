package com.example.imy_server.Repository.pulse;

import com.example.imy_server.Domain.Pulse.PulseDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PulseDateRepository extends JpaRepository<PulseDate,Long> {
    List<PulseDate> findAll();
    Boolean existsByCreatedDate(LocalDate date);
    PulseDate findPulseDateByCreatedDate(LocalDate date);

}
