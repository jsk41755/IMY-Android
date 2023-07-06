package com.example.imy_server.Repository.pulse;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import com.example.imy_server.Domain.Pulse.StrangePulse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StrangePulseRepository extends JpaRepository<StrangePulse,Long> {

    @Query("SELECT count(sp.strSeq) " +
            "FROM StrangePulse sp "+
            "WHERE sp.pulseDate.createdDate = :date "
    )
    String GetStrangePulseCount(LocalDate date);

    @Query("SELECT count(sp.strSeq) " +
            "FROM StrangePulse sp "
    )
    String CountAllStrangePulse();
}
