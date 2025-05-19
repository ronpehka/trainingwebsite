package com.bcs.trainingwebsite.persistance.weekday;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "weekday", schema = "training")
public class Weekday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "short", nullable = false, length = Integer.MAX_VALUE)
    private String shortField;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

}