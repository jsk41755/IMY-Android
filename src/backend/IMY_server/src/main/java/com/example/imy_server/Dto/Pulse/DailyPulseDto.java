package com.example.imy_server.Dto.Pulse;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class DailyPulseDto {
    private LocalDate createdDate;
    private List<Pulse> pulseList;

    public DailyPulseDto(PulseDate pulseDate) {
        this.createdDate = pulseDate.getCreatedDate();
        this.pulseList = pulseDate.getPulse();
    }
}
