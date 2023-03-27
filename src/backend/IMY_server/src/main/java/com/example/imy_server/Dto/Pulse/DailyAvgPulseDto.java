package com.example.imy_server.Dto.Pulse;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DailyAvgPulseDto {
    private LocalDate createdDate;
    private String aValue;

    public DailyAvgPulseDto(LocalDate createdDate, String aValue) {
        this.createdDate = createdDate;
        this.aValue = aValue;
    }
}
