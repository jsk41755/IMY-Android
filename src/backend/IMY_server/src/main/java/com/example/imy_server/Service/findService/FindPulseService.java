package com.example.imy_server.Service.findService;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Dto.Pulse.DailyPulseDto;
import com.example.imy_server.Repository.pulse.PulseDateRepository;
import com.example.imy_server.Repository.pulse.PulseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindPulseService {

    @Autowired
    PulseRepository pulseRepository;
    @Autowired
    PulseDateRepository pulseDateRepository;
    /**
     * 모든 맥박을 가져오기 위한 함수
     * @return
     */
    @Transactional(readOnly = true)
    public List<Pulse> getAllPulse(){
        return pulseRepository.findAll();
    }

    /**
     * 날짜별 맥박을 가져오기 위한 함수
     * @return
     */
    @Transactional(readOnly = true)
    public List<DailyPulseDto> getAllDailyPulse(){
        return pulseDateRepository.findAll().stream().map(DailyPulseDto::new).collect(Collectors.toList());
    }
}
