package com.example.imy_server.Domain.Pulse;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

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
    private String strPulseValue;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dateSeq")
    private PulseDate pulseDate;

    public StrangePulse(LocalTime createdTime, String strPulseValue) {
        this.createdTime = createdTime;
        this.strPulseValue = strPulseValue;
    }
}
