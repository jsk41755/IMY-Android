package com.example.imy_server.Controller;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.StrangePulse;
import com.example.imy_server.Dto.Pulse.*;
import com.example.imy_server.Service.PulseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pulse")
@RequiredArgsConstructor // 생성자 주입
public class PulseController {

    private final PulseServiceImpl pulseServiceImpl;

    /**
     * 일별 측정된 모든 맥박값을 가져오는 함수
     * @return
     */
    @GetMapping("/total")
    public List<DailyPulseDto> GetPulse(){

        //pulseDto 값에 맞게 pulse 값을 뽑음.
        return pulseServiceImpl.getAllDailyPulse();
    }

    /**
     * 주기별로 측정된 모든 맥박값을 날짜에 맞게 저장하는 함수.
     * @param dailyPulseDto
     */
    @PostMapping("/total")
    public void PostPulse(@RequestBody DailyPulseDto dailyPulseDto){

        /**
         * PulseDto로 변경해야함.
         */
        //리스트 형태의 시간당 맥박들
        List<Pulse> pulses = dailyPulseDto.getPulseList();
        //맥박이 측정된 날짜
        LocalDate pdate = dailyPulseDto.getCreatedDate();
        //일일 모든 맥박 저장 및 일일 평균 맥박 저장
        pulseServiceImpl.insertPulse(pulses,pdate);
    }

    /**
     * 일일 평균 맥박 가져오기
     */
    @GetMapping("/daily/avg/{date}")
    public AvgPulseDto GetDailyAvgPulse(
        @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return pulseServiceImpl.getDailyAvgPulse(date);
    }

    /**
     * 전체 평균 맥박 가져오기
     */
    @GetMapping("/all/pulse/avg")
    public String GetAllPulseAvg(){
        return pulseServiceImpl.getAllPulsesAvg();
    }

    /**
     * 전체 일일 평균 맥박 가져오기
     * @return
     */
    @GetMapping("/daily/avg/all")
    public List<DailyAvgPulseDto> GetAllDailyAvgPulse(){
        return pulseServiceImpl.getAllDailyAvgPulse();
    }

    /**
     * 이상맥박값 dump 값을 저장하기 위한 함수
     * 총 발생 이상값 저장.
     */
    @PostMapping("/total/str")
    public void PostDailyStrangePulse(@RequestBody DailyStrangePulseDto dailyStrangePulseDto){

        //리스트 형태의 시간당 이상맥박들
        List<StrangePulse> pulses = dailyStrangePulseDto.getPulseList();
        //이상맥박이 측정된 날짜
        LocalDate pdate = dailyStrangePulseDto.getCreatedDate();
        //일일 모든 이상맥박 저장
        pulseServiceImpl.insertDailyStrangePulse(pulses, pdate);

    }
    @GetMapping("/daily/str")
    public List<DailyStrangePulseDto> GetAllDailyStrangePulse(){
        return pulseServiceImpl.GetAllDailyStrangePulse();
    }

    @GetMapping("/daily/str/cnt/{date}")
    public StrPulseDto GetDailyStrPulseCnt(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return pulseServiceImpl.CountDailyStrangePulse(date);
    }

    @GetMapping("/all/str/cnt")
    public String GetAllStrPulseCnt(){
        return pulseServiceImpl.CountAllStrPulse();
    }
}
