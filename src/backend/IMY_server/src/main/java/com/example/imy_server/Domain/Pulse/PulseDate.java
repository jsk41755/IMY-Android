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

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dateSeq;
    @Id
    private LocalDate createdDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "pulseDate",fetch = FetchType.LAZY)
    private List<Pulse> pulse = new ArrayList<>();

    public PulseDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
