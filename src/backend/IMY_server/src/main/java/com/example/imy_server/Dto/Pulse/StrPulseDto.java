package com.example.imy_server.Dto.Pulse;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class StrPulseDto {
    private LocalDate createdDate;
    private String strValue;

    public StrPulseDto(LocalDate createdDate, String strValue) {
        this.createdDate = createdDate;
        this.strValue = strValue;
    }
}
