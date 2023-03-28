package com.example.imy_server.Domain.Pulse;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StrangePulse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long strSeq;
    private LocalTime createdTime;
    private String strValue;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dateSeq")
    private PulseDate pulseDate;

    public StrangePulse(LocalTime createdTime, String strValue) {
        this.createdTime = createdTime;
        this.strValue = strValue;
    }
}
