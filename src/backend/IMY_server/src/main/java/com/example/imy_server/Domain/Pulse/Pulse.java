package com.example.imy_server.Domain.Pulse;

import com.example.imy_server.Dto.Pulse.PulseDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pulse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pulseSeq;
    private LocalTime createdTime;
    private String pulseValue;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="createdDate")
    private PulseDate pulseDate;

    public Pulse(LocalTime createdTime, String pulseValue) {
        this.createdTime = createdTime;
        this.pulseValue = pulseValue;
    }
}
