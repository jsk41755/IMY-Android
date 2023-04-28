package com.example.imy_server.Domain.Pulse;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AvgPulse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avgSeq;
    private String avgValue;
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval=true, cascade = CascadeType.ALL)
    @JoinColumn(name="dateSeq")
    private PulseDate pulseDate;

    public AvgPulse(String avgValue, PulseDate pulseDate) {
        this.avgValue = avgValue;
        this.pulseDate = pulseDate;
    }
}
