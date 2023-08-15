package com.example.imy_server.Service.saveService;

import com.example.imy_server.Domain.Pulse.PulseDate;
import com.example.imy_server.Domain.Pulse.StrangePulse;
import com.example.imy_server.Repository.pulse.PulseDateRepository;
import com.example.imy_server.Repository.pulse.StrangePulseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaveStrangePulseService {

    @Autowired
    StrangePulseRepository strangePulseRepository;
    @Autowired
    PulseDateRepository pulseDateRepository;
    /**
     * 이상 맥박을 저장하기 위한 함수.
     * 이상맥박 dump 저장을 위한 임시 함수 (실제 서비스에는 사용하지 않을 것)
     * @param pulses
     * @param date
     */
    @Transactional
    public void insertDailyStrangePulse(List<StrangePulse> pulses, LocalDate date){
        //날짜가 존재하는지 확인하고
        if(pulseDateRepository.existsByCreatedDate(date)) { //날짜가 존재하면 해당하는 날짜에 pulse를 파싱해서 넣기
            PulseDate pulseDate = pulseDateRepository.findPulseDateByCreatedDate(date);
            List<StrangePulse> pulseList = new ArrayList<>();
            for (StrangePulse p : pulses) {
                StrangePulse strPulse = new StrangePulse(
                        p.getCreatedTime(),
                        p.getStrPulseValue()
                );
                strPulse.setPulseDate(pulseDate);
                pulseList.add(strPulse);
            }
            //맥박 리스트 저장
            strangePulseRepository.saveAllAndFlush(pulseList);

        }else { //날짜가 존재하지 않으면 해당하는 맥박과 해당하는 날짜를 넣음
            PulseDate pulseDate = new PulseDate(date);
            List<StrangePulse> pulseList = new ArrayList<>();
            for (StrangePulse p : pulses) {
                StrangePulse strPulse = new StrangePulse(
                        p.getCreatedTime(),
                        p.getStrPulseValue()
                );
                strPulse.setPulseDate(pulseDate);
                pulseList.add(strPulse);
            }
            //맥박 리스트 저장
            strangePulseRepository.saveAllAndFlush(pulseList);
        }
    }
}
