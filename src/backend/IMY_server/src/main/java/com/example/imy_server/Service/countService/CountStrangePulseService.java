package com.example.imy_server.Service.countService;

import com.example.imy_server.Dto.Pulse.StrPulseDto;
import com.example.imy_server.Repository.pulse.StrangePulseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CountStrangePulseService {

    @Autowired
    StrangePulseRepository strangePulseRepository;
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
