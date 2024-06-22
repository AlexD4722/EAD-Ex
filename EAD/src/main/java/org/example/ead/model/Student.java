package org.example.ead.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "student_t")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    @NotNull
    int id;
    @NotNull
    @Column(name = "student_code")
    String code;
    @NonNull
    @Column(name = "full_name")
    String name;
    @NonNull
    @Column(name = "address")
    String address;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    List<Score> scores;


}
