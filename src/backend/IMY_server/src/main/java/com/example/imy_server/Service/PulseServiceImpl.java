package com.example.imy_server.Service;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import com.example.imy_server.Domain.Pulse.StrangePulse;
import com.example.imy_server.Dto.Pulse.*;
import com.example.imy_server.Repository.pulse.AvgPulseRepository;
import com.example.imy_server.Repository.pulse.PulseDateRepository;
import com.example.imy_server.Repository.pulse.PulseRepository;
import com.example.imy_server.Repository.pulse.StrangePulseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //생성자 주입을 위함.
public class PulseServiceImpl {
    private final PulseRepository pulseRepository;
    private final PulseDateRepository pulseDateRepository;
    private final AvgPulseRepository avgPulseRepository;
    private final StrangePulseRepository strangePulseRepository;
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

    /**
     * 일일 맥박을 저장하기 위한 함수
     * @param pulses
     * @param date
     */
    @Transactional
    public void insertPulse(List<Pulse>pulses, LocalDate date){

        //날짜가 존재하는지 확인하고
        if(pulseDateRepository.existsByCreatedDate(date)) { //날짜가 존재하면 해당하는 날짜에 pulse를 파싱해서 넣기
            PulseDate pulseDate = pulseDateRepository.findPulseDateByCreatedDate(date);
            savePulse(pulses, pulseDate);
            //변경된 맥박 값 평균 가져옴
            updateAvgPulse(date, pulseDate);
            // 이상맥박 객체를 만들어 줘야함.

            // 이상맥박 저장
            saveStrangePulse(pulses, pulseDate);
        }else{ //날짜가 존재하지 않으면 해당하는 맥박과 해당하는 날짜를 넣음
            PulseDate pulseDate = new PulseDate(date);
            savePulse(pulses, pulseDate);
            //변경된 맥박 값 평균 가져옴
            updateAvgPulse(date, pulseDate);

            //이상 맥박 저장
            saveStrangePulse(pulses, pulseDate);
        }
    }

    /**
     * Pulse 객체를 StrPulse 객체로 변환하는 작업
     * 어댑터 패턴을 적용해 Pulse 와 StrPulse를 쉽게 변환 가능한지 확인 할 것.
     * @param pulses
     * @param pulseDate
     * @return
     */
    private List<StrangePulse> findStrangePulse(List<Pulse>pulses, PulseDate pulseDate){
        
        //이상맥박 분류

        //이상맥박 리스트 생성
        List<StrangePulse> strPulseList = new ArrayList<>();
        for (Pulse p : pulses) {
            StrangePulse strPulse = new StrangePulse(
                    p.getCreatedTime(),
                    p.getPulseValue()
            );
            strPulse.setPulseDate(pulseDate);
            strPulseList.add(strPulse);
        }
        return strPulseList;
    }

    private void updateAvgPulse(LocalDate date, PulseDate pulseDate) {
        String dailyAvgPulse = pulseRepository.GetAvgPulseValueByDate(date);
        //맥박값 평균 저장
        avgPulseRepository.UpdatePulseByAvgSeq(dailyAvgPulse, pulseDate, pulseDate.getDateSeq());
    }

    /**
     * 맥박을 저장하기 위한 함수
     * @param pulses
     * @param pulseDate
     */
    private void savePulse(List<Pulse> pulses, PulseDate pulseDate) {
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
    }

    /**
     * 맥박을 받아 이상맥박을 만들어 이상맥박을 저장하기 위한 함수
     * @param pulses
     * @param pulseDate
     */
    private void saveStrangePulse(List<Pulse> pulses, PulseDate pulseDate) {
        //이상맥박을 찾아 리스트에 저장
        List<StrangePulse>strangePulseList =  findStrangePulse(pulses,pulseDate);
        //맥박 리스트 저장
        strangePulseRepository.saveAllAndFlush(strangePulseList);
    }
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
    
    /**
     * 일별로 발생한 모든 이상맥박 을 가져오기 위한 함수
     */
    @Transactional(readOnly = true)
    public List<DailyStrangePulseDto> GetAllDailyStrangePulse(){
        return pulseDateRepository.findAll().stream().map(DailyStrangePulseDto::new).collect(Collectors.toList());
    }

    /**
     * 일별 발생한 이상 맥박을 세서 반환하는 함수.
     */
    @Transactional(readOnly = true)
    public StrPulseDto CountDailyStrangePulse(LocalDate date){
        String strNum = strangePulseRepository.GetStrangePulseCount(date);
        StrPulseDto strPulseDto = new StrPulseDto(date, strNum);

        return strPulseDto;
    }

    @Transactional(readOnly = true)
    public String CountAllStrPulse(){
        return strangePulseRepository.CountAllStrangePulse();
    }
}
