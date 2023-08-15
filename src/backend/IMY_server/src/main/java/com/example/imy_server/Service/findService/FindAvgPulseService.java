package com.example.imy_server.Service.findService;

import com.example.imy_server.Dto.Pulse.AvgPulseDto;
import com.example.imy_server.Dto.Pulse.DailyAvgPulseDto;
import com.example.imy_server.Repository.pulse.AvgPulseRepository;
import com.example.imy_server.Repository.pulse.PulseDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindAvgPulseService {

    @Autowired
    AvgPulseRepository avgPulseRepository;
    @Autowired
    PulseDateRepository pulseDateRepository;
    /**
     * 일일 평균 맥박을 가져오기 위한 함수
     * @param date
     * @return
     */
    @Transactional(readOnly = true)
    public AvgPulseDto getDailyAvgPulse(LocalDate date){

        String dailyAvgPulse = avgPulseRepository.findDailyAvgPulseByDate(date);
        AvgPulseDto avgPulseDto = new AvgPulseDto(date, dailyAvgPulse);

        return avgPulseDto;
    }

    /**
     * 전체 평균 맥박을 가져오기 위한 함수
     */
    @Transactional(readOnly = true)
    public List<DailyAvgPulseDto> getAllDailyAvgPulse(){
        return pulseDateRepository.findAll().stream().map(DailyAvgPulseDto::new).collect(Collectors.toList());
    }

    /**
     * 전체 맥박의 평균을 가져오기 위한 함수
     */
    @Transactional(readOnly = true)
    public String getAllPulsesAvg(){
        return avgPulseRepository.AllPulsesAvg();
    }

}
