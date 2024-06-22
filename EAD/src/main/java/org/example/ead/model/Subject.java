package org.example.ead.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "subject_t")
public class Subject {
    @Id
    @NonNull
    @Column(name = "subject_id")
    int id;
    @NonNull
    @Column(name = "subject_code")
    String code;
    @NonNull
    @Column(name = "subject_name")
    String name;
    @NonNull
    @Column(name = "credit")
    int credit;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    List<Score> scores;
}
