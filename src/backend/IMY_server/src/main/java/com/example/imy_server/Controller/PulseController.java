package com.example.imy_server.Controller;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.StrangePulse;
import com.example.imy_server.Dto.Pulse.*;
import com.example.imy_server.Service.PulseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pulse")
@RequiredArgsConstructor // 생성자 주입
public class PulseController {

    private final PulseService pulseService;

    /**
     * 일별 측정된 모든 맥박값을 가져오는 함수
     * @return
     */
    @GetMapping("/total")
    public List<DailyPulseDto> GetPulse(){

        //pulseDto 값에 맞게 pulse 값을 뽑음.
        return pulseService.getAllDailyPulse();
    }

    /**
     * 주기별로 측정된 모든 맥박값을 날짜에 맞게 저장하는 함수.
     * @param dailyPulseDto
     */
    @PostMapping("/total")
    public void PostPulse(@RequestBody DailyPulseDto dailyPulseDto){

        //리스트 형태의 시간당 맥박들
        List<Pulse> pulses = dailyPulseDto.getPulseList();
        //맥박이 측정된 날짜
        LocalDate pdate = dailyPulseDto.getCreatedDate();
        //일일 모든 맥박 저장 및 일일 평균 맥박 저장
        pulseService.insertPulse(pulses,pdate);
    }

    /**
     * 전체 평균 맥박 가져오기
     * 평균 맥박은 daily 맥박이 들어올때 마다 그때그때 같이 업데이트
     */
    @GetMapping("/total/avg")
    public void GetAvgPulse(){

    }

    /**
     * 일일 평균 맥박 가져오기
     */
    @GetMapping("/daily/avg/{date}")
    public AvgPulseDto GetDailyAvgPulse(
        @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return pulseService.getDailyAvgPulse(date);
    }

    /**
     * 전체 평균 맥박 가져오기
     */
    @GetMapping("/all/pulse/avg")
    public String GetAllPulseAvg(){
        return pulseService.getAllPulsesAvg();
    }

    /**
     * 전체 일일 평균 맥박 가져오기
     * @return
     */
    @GetMapping("/daily/avg/all")
    public List<DailyAvgPulseDto> GetAllDailyAvgPulse(){
        return pulseService.getAllDailyAvgPulse();
    }

    /**
     * 총 발생 이상값 저장.
     */
    @PostMapping("/total/str")
    public void PostDailyStrangePulse(@RequestBody DailyStrangePulseDto dailyStrangePulseDto){

        //리스트 형태의 시간당 이상맥박들
        List<StrangePulse> pulses = dailyStrangePulseDto.getPulseList();
        //이상맥박이 측정된 날짜
        LocalDate pdate = dailyStrangePulseDto.getCreatedDate();
        //일일 모든 이상맥박 저장
        pulseService.insertDailyStrangePulse(pulses, pdate);

    }
    @GetMapping("/daily/str")
    public List<DailyStrangePulseDto> GetAllDailyStrangePulse(){
        return pulseService.GetAllDailyStrangePulse();
    }

    @GetMapping("/daily/str/cnt/{date}")
    public StrPulseDto GetDailyStrPulseCnt(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return pulseService.CountDailyStrangePulse(date);
    }

    @GetMapping("/all/str/cnt")
    public String GetAllStrPulseCnt(){
        return pulseService.CountAllStrPulse();
    }
}
