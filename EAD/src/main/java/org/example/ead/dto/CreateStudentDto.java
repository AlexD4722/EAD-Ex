package org.example.ead.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.ead.util.Grade;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateStudentDto {
    String code;
    String name;
    String address;
}
