package com.example.imy_server.Dto.Pulse;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AvgPulseDto {
    private LocalDate createdDate;
    private String averageValue;

    public AvgPulseDto(LocalDate createdDate, String averageValue) {
        this.createdDate = createdDate;
        this.averageValue = averageValue;
    }
}
