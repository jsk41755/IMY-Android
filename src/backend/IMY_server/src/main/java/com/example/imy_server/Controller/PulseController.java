package com.example.imy_server.Controller;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import com.example.imy_server.Dto.Pulse.DailyPulseDto;
import com.example.imy_server.Service.AvgPulseService;
import com.example.imy_server.Service.PulseService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pulse")
@RequiredArgsConstructor // 생성자 주입
public class PulseController {

    private final PulseService pulseService;
    private final AvgPulseService avgPulseService;

    @GetMapping("/total")
    public List<DailyPulseDto> GetPulse(){

        //pulseDto 값에 맞게 pulse 값을 뽑음.
        return pulseService.getAllPulseByDate();
    }

    @PostMapping("/total")
    public void InsertPulse(@RequestBody DailyPulseDto dailyPulseDto){

        //리스트 형태의 시간당 맥박들
        List<Pulse> pulses = dailyPulseDto.getPulseList();
        //맥박이 측정된 날짜
        LocalDate pdate = dailyPulseDto.getCreatedDate();
        //일일 모든 맥박 저장
        pulseService.insertPulse(pulses,pdate);
        //일일 평균 맥박 저장
        avgPulseService.insertAvgPulse(pulses,pdate);
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
    public void GetDailyAvgPulse(@PathVariable("date") LocalDate date){

    }
}
