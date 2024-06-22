package org.example.ead.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.example.ead.dto.CreateStudentDto;
import org.example.ead.dto.StudentDto;
import org.example.ead.dao.StudentDao;
import org.example.ead.dto.StudentDtoBasic;
import org.example.ead.model.Score;
import org.example.ead.model.Student;
import org.example.ead.model.Subject;
import org.example.ead.util.GradeCalculator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentDaoImpl implements StudentDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<StudentDto> findAllDto() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<Student> root = query.from(Student.class);

        //Join Score
        Join<Student, Score> scoreJoin = root.join("scores", JoinType.LEFT);
        // Join Subject
        Join<Score, Subject> subjectJoin = scoreJoin.join("subject", JoinType.LEFT);


        query.multiselect(
                root.get("id").alias("id"),
                root.get("code").alias("code"),
                root.get("name").alias("name"),
                subjectJoin.get("name").alias("subjectName"),
                scoreJoin.get("score1").alias("score1"),
                scoreJoin.get("score2").alias("score2"),
                subjectJoin.get("credit").alias("credit")
        );
        query.distinct(true);
        List<Tuple> results = entityManager.createQuery(query).getResultList();
        //check if the result is empty
        if (results.isEmpty()) {
            return Collections.emptyList();
        }
        // Convert List<Tuple> to StudentDto
        return results.stream()
                .map(result -> {
                    StudentDto dto = new StudentDto();
                    dto.setId(result.get("id", Integer.class));
                    dto.setCode(result.get("code", String.class));
                    dto.setName(result.get("name", String.class));
                    dto.setSubjectName(result.get("subjectName", String.class));
                    BigDecimal score1 = result.get("score1", BigDecimal.class);
                    BigDecimal score2 = result.get("score2", BigDecimal.class);
                    dto.setScore1(score1);
                    dto.setScore2(score2);
                    Integer credit = result.get("credit", Integer.class);
                    dto.setCredit(credit != null ? credit : 0);

                    // Calculate grade
                    if (score1 != null && score2 != null) {
                        dto.setGrade(GradeCalculator.calculateGrade(score1, score2));
                    } else {
                        dto.setGrade(null);
                    }

                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<StudentDtoBasic> findAllDtoBasic() {
        // Use CriteriaBuilder to create a CriteriaQuery<Tuple>
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentDtoBasic> query = cb.createQuery(StudentDtoBasic.class);
        Root<Student> root = query.from(Student.class);
        // Select only id, code, name, and address
        query.select(cb.construct(StudentDtoBasic.class,
                root.get("id"),
                root.get("code"),
                root.get("name"),
                root.get("address")
        ));
        // Return the result
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public Boolean save(CreateStudentDto createStudentDto) {
        // Convert CreateStudentDto to Student
        Student student = new Student();
        student.setCode(createStudentDto.getCode());
        student.setName(createStudentDto.getName());
        student.setAddress(createStudentDto.getAddress());
        // Use EntityManager to save the Student object
        try {
            entityManager.persist(student);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Student findById(int id) {
        // Use EntityManager to find the Student object by id
        try {
            return entityManager.find(Student.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
