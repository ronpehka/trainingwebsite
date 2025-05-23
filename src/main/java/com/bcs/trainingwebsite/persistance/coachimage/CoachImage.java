package com.bcs.trainingwebsite.persistance.coachimage;

import com.bcs.trainingwebsite.persistance.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coach_image", schema = "training")
public class CoachImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id;

    @NotNull
    @Column(name = "data", nullable = false)
    private byte[] data;

}