package com.example.imy_server.Service;

import com.example.imy_server.Domain.Pulse.AvgPulse;
import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import com.example.imy_server.Dto.Pulse.DailyAvgPulseDto;
import com.example.imy_server.Dto.Pulse.DailyPulseDto;
import com.example.imy_server.Repository.pulse.AvgPulseRepository;
import com.example.imy_server.Repository.pulse.PulseDateRepository;
import com.example.imy_server.Repository.pulse.PulseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 생성자 주입
public class AvgPulseService {
    private final PulseDateRepository pulseDateRepository;
    private final PulseRepository pulseRepository;
    private final AvgPulseRepository avgPulseRepository;
    @Transactional
    public void insertAvgPulse(List<Pulse> pulses, LocalDate date){

        PulseDate pulseDate = pulseDateRepository.findPulseDateByCreatedDate(date);
        String dailyAvgPulse = pulseRepository.GetAvgPulseValueByDate(date);
        avgPulseRepository.UpdatePulseByAvgSeq(dailyAvgPulse,pulseDate, pulseDate.getDateSeq());

    }
}
