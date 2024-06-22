package org.example.ead.dto;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "student_score_t")
public class CreateScoreDto{
    BigDecimal score1;
    BigDecimal score2;
    int studentId;
    int subjectId;
}
