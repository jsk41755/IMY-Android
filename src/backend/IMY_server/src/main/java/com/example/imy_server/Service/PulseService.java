package com.example.imy_server.Service;

import com.example.imy_server.Domain.Pulse.AvgPulse;
import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //생성자 주입을 위함.
public class PulseService {
    private final PulseRepository pulseRepository;
    private final PulseDateRepository pulseDateRepository;
    private final AvgPulseRepository avgPulseRepository;
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
    public List<DailyPulseDto> getAllPulseByDate(){
        return pulseDateRepository.findAll().stream().map(DailyPulseDto::new).collect(Collectors.toList());
    }

    /**
     * 맥박을 저장하기 위한 함수
     * @param pulses
     * @param date
     */
    @Transactional
    public void insertPulse(List<Pulse>pulses, LocalDate date){

        //날짜가 존재하는지 확인하고
        if(pulseDateRepository.existsByCreatedDate(date)) { //날짜가 존재하면 해당하는 날짜에 pulse를 파싱해서 넣기
            PulseDate pulseDate = pulseDateRepository.findPulseDateByCreatedDate(date);
            List<Pulse> pulseList = new ArrayList<>();
            for (Pulse p : pulses) {
                Pulse pulse = new Pulse(
                        p.getCreatedTime(),
                        p.getPulseValue()
                );
                pulse.setPulseDate(pulseDate);
                pulseList.add(pulse);
            }

            //맥박 리스트 저장
            pulseRepository.saveAllAndFlush(pulseList);
            //변경된 맥박 값 평균 가져옴
            String dailyAvgPulse = pulseRepository.GetAvgPulseValueByDate(date);
            //맥박값 평균 저장
            avgPulseRepository.UpdatePulseByAvgSeq(dailyAvgPulse,pulseDate, pulseDate.getDateSeq());

        }else{ //날짜가 존재하지 않으면 해당하는 맥박과 해당하는 날짜를 넣음
            PulseDate pulseDate = new PulseDate(date);
            List<Pulse> pulseList = new ArrayList<>();
            for (Pulse p : pulses) {
                Pulse pulse = new Pulse(
                        p.getCreatedTime(),
                        p.getPulseValue()
                );
                pulse.setPulseDate(pulseDate);
                pulseList.add(pulse);
            }
            //맥박 리스트 저장
            pulseRepository.saveAllAndFlush(pulseList);
            //변경된 맥박 값 평균 가져옴
            String dailyAvgPulse = pulseRepository.GetAvgPulseValueByDate(date);
            //맥박값 평균 저장
            avgPulseRepository.UpdatePulseByAvgSeq(dailyAvgPulse,pulseDate, pulseDate.getDateSeq());

        }
    }
}
