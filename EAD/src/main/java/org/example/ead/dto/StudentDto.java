package org.example.ead.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.ead.model.Score;
import org.example.ead.util.Grade;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDto {
    int id;
    String code;
    String name;
    String subjectName;
    BigDecimal score1;
    BigDecimal score2;
    int credit;
    Grade grade;
}
