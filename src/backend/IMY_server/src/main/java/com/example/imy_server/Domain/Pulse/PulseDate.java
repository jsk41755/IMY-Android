package com.example.imy_server.Domain.Pulse;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PulseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateSeq;

    private LocalDate createdDate;

    /**
     * 측정맥박은 하루에 여러개일 수 있다.
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "pulseDate",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Pulse> pulse = new ArrayList<>();
    /**
     * 이상맥박은 하루에 여러개일 수 있다
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "pulseDate",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<StrangePulse> strPulse = new ArrayList<>();
    /**
     * 평균맥박은 하루에 하나만 존재한다.
     */
    @JsonManagedReference
    @OneToOne(mappedBy = "pulseDate",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private AvgPulse avgPulse = new AvgPulse();

    public PulseDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
