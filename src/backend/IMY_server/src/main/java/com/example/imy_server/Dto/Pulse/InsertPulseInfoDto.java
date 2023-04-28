package com.example.imy_server.Dto.Pulse;

import com.example.imy_server.Domain.Pulse.Pulse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class InsertPulseInfoDto {

    private LocalDate localDate;
    private List<Pulse> pulse;

}
