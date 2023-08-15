package com.example.imy_server.Service.findService;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import com.example.imy_server.Domain.Pulse.StrangePulse;
import com.example.imy_server.Dto.Pulse.DailyStrangePulseDto;
import com.example.imy_server.Repository.pulse.PulseDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindStrangePulseService {

    @Autowired
    PulseDateRepository pulseDateRepository;
    /**
     * 맥박 List에서 이상맥박을 찾아 이상맥박List로 반환하는 함수.
     * 어댑터 패턴을 적용해 Pulse 와 StrPulse를 쉽게 변환 가능한지 확인 할 것.
     * @param pulses
     * @param pulseDate
     * @return
     */
    @Transactional
    public List<StrangePulse> findStrangePulse(List<Pulse>pulses, PulseDate pulseDate){

        //이상맥박 리스트 생성
        List<StrangePulse> strangePulseList = new ArrayList<>();
        //이상맥박 분류
        for (Pulse p : pulses) {
            //맥박을 꺼내서
            Integer pulse = Integer.parseInt(p.getPulseValue());
            //맥박이 90보다 높으면 이상맥박으로 구분.
            if(pulse>=90){
                StrangePulse strangePulse = new StrangePulse(
                        p.getCreatedTime(),
                        p.getPulseValue()
                );
                strangePulse.setPulseDate(pulseDate);
                strangePulseList.add(strangePulse);
            }
        }
        return strangePulseList;
    }

    /**
     * 일별로 발생한 모든 이상맥박 을 가져오기 위한 함수
     */
    @Transactional(readOnly = true)
    public List<DailyStrangePulseDto> GetAllDailyStrangePulse(){
        return pulseDateRepository.findAll().stream().map(DailyStrangePulseDto::new).collect(Collectors.toList());
    }
}
