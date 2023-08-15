package com.example.imy_server.Service.updateService;

import com.example.imy_server.Domain.Pulse.PulseDate;
import com.example.imy_server.Repository.pulse.AvgPulseRepository;
import com.example.imy_server.Repository.pulse.PulseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UpdateAvgPulseService {

    @Autowired
    PulseRepository pulseRepository;
    @Autowired
    AvgPulseRepository avgPulseRepository;

    public void updateAvgPulse(LocalDate date, PulseDate pulseDate) {
        String dailyAvgPulse = pulseRepository.GetAvgPulseValueByDate(date);
        //맥박값 평균 저장
        avgPulseRepository.UpdatePulseByAvgSeq(dailyAvgPulse, pulseDate, pulseDate.getDateSeq());
    }
}
