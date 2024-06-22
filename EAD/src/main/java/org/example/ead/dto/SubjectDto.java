package org.example.ead.dto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.ead.model.Score;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectDto{
    int id;
    String code;
    String name;
    int credit;
}
