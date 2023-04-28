package com.example.imy_server.Dto.Pulse;

import com.example.imy_server.Domain.Pulse.AvgPulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class DailyAvgPulseDto {
    private LocalDate createdDate;
    private AvgPulse  averageValue;

    public DailyAvgPulseDto(PulseDate pulseDate) {
        this.createdDate = pulseDate.getCreatedDate();
        this.averageValue = pulseDate.getAvgPulse();
    }
}