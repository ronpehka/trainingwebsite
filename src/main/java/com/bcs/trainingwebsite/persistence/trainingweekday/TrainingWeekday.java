package com.bcs.trainingwebsite.persistence.trainingweekday;

import com.bcs.trainingwebsite.persistence.weekday.Weekday;
import com.bcs.trainingwebsite.persistence.training.Training;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "training_weekday", schema = "training")
public class TrainingWeekday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weekday_id", nullable = false)
    private Weekday weekday;

}