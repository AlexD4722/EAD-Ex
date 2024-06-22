package org.example.ead.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.ead.util.Grade;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDtoBasic{
    int id;
    String code;
    String name;
    String address;
}
