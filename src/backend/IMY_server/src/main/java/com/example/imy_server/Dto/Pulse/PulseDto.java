package com.example.imy_server.Dto.Pulse;

import com.example.imy_server.Domain.Pulse.Pulse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PulseDto {

    private LocalTime createdTime;
    private String pulseValue;

    public PulseDto(Pulse pulse) {
        this.createdTime = pulse.getCreatedTime();
        this.pulseValue = pulse.getPulseValue();
    }
}
