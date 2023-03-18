package com.example.imy_server.Controller;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import com.example.imy_server.Dto.Pulse.DailyPulseDto;
import com.example.imy_server.Service.PulseService;
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

    @GetMapping
    public List<DailyPulseDto> GetPulse(){

        //pulseDto 값에 맞게 pulse 값을 뽑음.
        return pulseService.getAllPulseByDate();
    }

    @PostMapping
    public void InsertPulse(@RequestBody DailyPulseDto dailyPulseDto){

        List<Pulse> pulses = dailyPulseDto.getPulseList();
        LocalDate pdate = dailyPulseDto.getCreatedDate();
        System.out.println(pdate);
        pulseService.insertPulse(pulses,pdate);
    }
}
