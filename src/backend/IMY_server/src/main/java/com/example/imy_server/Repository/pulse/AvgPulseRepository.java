package com.example.imy_server.Repository.pulse;

import com.example.imy_server.Domain.Pulse.AvgPulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvgPulseRepository extends JpaRepository<AvgPulse,Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE AvgPulse ap "+
            "SET ap.avgValue = :avgValue, ap.pulseDate = :pulseDate "+
            "WHERE ap.avgSeq = :dateSeq "
    )
    void UpdatePulseByAvgSeq(String avgValue, PulseDate pulseDate, Long dateSeq);

    @Query("SELECT ap.avgValue " +
           "FROM AvgPulse ap " +
           "WHERE ap.pulseDate.createdDate = :date"
    )
    String findDailyAvgPulseByDate(LocalDate date);

    @Query("SELECT FLOOR(AVG(ap.avgValue)) "+
            "FROM AvgPulse ap "
    )
    String AllPulsesAvg();
}
