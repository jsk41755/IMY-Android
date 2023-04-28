package com.example.imy_server.Dto.Pulse;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AvgPulseDto {
    private LocalDate createdDate;
    private String avgValue;

    public AvgPulseDto(LocalDate createdDate, String avgValue) {
        this.createdDate = createdDate;
        this.avgValue = avgValue;
    }
}
