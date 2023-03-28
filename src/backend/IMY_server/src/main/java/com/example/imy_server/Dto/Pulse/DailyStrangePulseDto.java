package com.example.imy_server.Dto.Pulse;

import com.example.imy_server.Domain.Pulse.Pulse;
import com.example.imy_server.Domain.Pulse.PulseDate;
import com.example.imy_server.Domain.Pulse.StrangePulse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class DailyStrangePulseDto {
    private LocalDate createdDate;
    private List<StrangePulse> pulseList;

    public DailyStrangePulseDto(PulseDate pulseDate) {
        this.createdDate = pulseDate.getCreatedDate();
        this.pulseList = pulseDate.getStrPulse();
    }
}
