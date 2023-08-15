package com.example.imy_server.Service.saveService;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import com.example.imy_server.Domain.Pulse.StrangePulse;
import com.example.imy_server.Repository.pulse.PulseDateRepository;
import com.example.imy_server.Repository.pulse.PulseRepository;
import com.example.imy_server.Repository.pulse.StrangePulseRepository;
import com.example.imy_server.Service.findService.FindStrangePulseService;
import com.example.imy_server.Service.updateService.UpdateAvgPulseService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavePulseService {


    private List<Pulse> pulses = new ArrayList<Pulse>();

    // 클래스 생성자 주입
    @Autowired
    private FindStrangePulseService findStrangePulseService;
    @Autowired
    private UpdateAvgPulseService updateAvgPulseService;
    @Autowired
    PulseRepository pulseRepository;
    @Autowired
    PulseDateRepository pulseDateRepository;
    @Autowired
    StrangePulseRepository strangePulseRepository;

    /**
     * 맥박을 저장하기 위한 함수
     */
    private void insertPulse(PulseDate pulseDate) {
        List<Pulse> pulseList = new ArrayList<>();
        for (Pulse p : this.pulses) {
            Pulse pulse = new Pulse(
                    p.getCreatedTime(),
                    p.getPulseValue()
            );
            pulse.setPulseDate(pulseDate);
            pulseList.add(pulse);
        }
        //맥박 리스트 저장
        pulseRepository.saveAllAndFlush(pulseList);
    }

    /**
     * 맥박을 받아 이상맥박을 만들어 이상맥박을 저장하기 위한 함수
     */
    private void insertStrangePulse(PulseDate pulseDate) {
        //이상맥박을 찾아 리스트에 저장
        List<StrangePulse>strangePulseList = findStrangePulseService.findStrangePulse(this.pulses,pulseDate);
        //맥박 리스트 저장
        strangePulseRepository.saveAllAndFlush(strangePulseList);
    }

    /**
     * 일일 맥박을 저장하기 위한 함수
     * @param pulses
     * @param date
     */
    @Transactional
    public void savePulse(List<Pulse> pulses, LocalDate date){
        this.pulses = pulses;
        //날짜가 존재하는지 확인하고
        if(pulseDateRepository.existsByCreatedDate(date)) { //날짜가 존재하면 해당하는 날짜에 pulse를 파싱해서 넣기
            PulseDate pulseDate = pulseDateRepository.findPulseDateByCreatedDate(date);
            insertPulse(pulseDate);
            //변경된 맥박 값 평균 가져옴

            updateAvgPulseService.updateAvgPulse(date, pulseDate);
            // 이상맥박 객체를 만들어 줘야함.

            // 이상맥박 저장
            insertStrangePulse(pulseDate);
        }else{ //날짜가 존재하지 않으면 해당하는 맥박과 해당하는 날짜를 넣음
            PulseDate pulseDate = new PulseDate(date);
            insertPulse(pulseDate);
            //변경된 맥박 값 평균 가져옴
            updateAvgPulseService.updateAvgPulse(date, pulseDate);

            //이상 맥박 저장
            insertStrangePulse(pulseDate);
        }
    }
}
