package com.example.imy_server.Domain.Pulse;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class StrangePulse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long strSeq;
    private String strValue;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dateSeq")
    private PulseDate pulseDate;

}
