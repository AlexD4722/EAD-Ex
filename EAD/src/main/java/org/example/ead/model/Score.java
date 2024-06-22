package org.example.ead.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "student_score_t")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "student_score_id")
    int id;
    @NotNull
    @Column(name = "score1")
    BigDecimal score1;
    @NotNull
    @Column(name = "score2")
    BigDecimal score2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    Student student;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    Subject subject;
}
