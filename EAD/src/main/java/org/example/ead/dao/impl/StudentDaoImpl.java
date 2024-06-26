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
import java.util.ArrayList;
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


        query.multiselect(root.get("id").alias("id"), root.get("code").alias("code"), root.get("name").alias("name"), subjectJoin.get("id").alias("subjectId"), subjectJoin.get("name").alias("subjectName"), scoreJoin.get("score1").alias("score1"), scoreJoin.get("score2").alias("score2"), subjectJoin.get("credit").alias("credit"));
        query.distinct(true);
        List<Tuple> results = entityManager.createQuery(query).getResultList();
        //check if the result is empty
        if (results.isEmpty()) {
            return Collections.emptyList();
        }
        // Convert List<Tuple> to StudentDto
        return results.stream().map(result -> {
            StudentDto dto = new StudentDto();
            dto.setId(result.get("id", Integer.class));
            dto.setCode(result.get("code", String.class));
            dto.setName(result.get("name", String.class));
            Integer subjectId = result.get("subjectId", Integer.class);
            dto.setSubjectId(subjectId != null ? subjectId : 0);
            dto.setSubjectName(result.get("subjectName", String.class));
            BigDecimal score1 = result.get("score1", BigDecimal.class);
            BigDecimal score2 = result.get("score2", BigDecimal.class);
            dto.setScore1(score1);
            dto.setScore2(score2);
            // Calculate grade
            if (score1 != null && score2 != null) {
                dto.setGrade(GradeCalculator.calculateGrade(score1, score2));
            } else {
                dto.setGrade(null);
            }
            Integer credit = result.get("credit", Integer.class);
            dto.setCredit(credit != null ? credit : 0);
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
        query.select(cb.construct(StudentDtoBasic.class, root.get("id"), root.get("code"), root.get("name"), root.get("address")));
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

    @Override
    public StudentDto findByIdDto(int id, int subjectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<Student> root = query.from(Student.class);

        //Join Score
        Join<Student, Score> scoreJoin = root.join("scores", JoinType.LEFT);
        // Join Subject
        Join<Score, Subject> subjectJoin = scoreJoin.join("subject", JoinType.LEFT);


        query.multiselect(root.get("id").alias("id"), root.get("code").alias("code"), root.get("name").alias("name"), subjectJoin.get("id").alias("subjectId"), subjectJoin.get("name").alias("subjectName"), scoreJoin.get("score1").alias("score1"), scoreJoin.get("score2").alias("score2"), subjectJoin.get("credit").alias("credit"));
        query.distinct(true);
        // Create a list of predicates
        List<Predicate> predicates = new ArrayList<>();
        // Always add the condition for the student id
        predicates.add(cb.equal(root.get("id"), id));

        // Only add the condition for the subject id if it is not 0
        if (subjectId != 0) {
            predicates.add(cb.equal(subjectJoin.get("id"), subjectId));
        }
        // Convert the list of predicates into an array and pass it to the where method
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        // Execute the query and return the result
        List<Tuple> results = entityManager.createQuery(query).getResultList();
        if (!results.isEmpty()) {
            // Get the first result
            Tuple result = results.get(0);

            // Convert Tuple to StudentDto
            StudentDto dto = new StudentDto();
            dto.setId(result.get("id", Integer.class));
            dto.setCode(result.get("code", String.class));
            dto.setName(result.get("name", String.class));
            Integer subjectIdResult = result.get("subjectId", Integer.class);
            dto.setSubjectId(subjectIdResult != null ? subjectIdResult : 0);
            dto.setSubjectName(result.get("subjectName", String.class));
            BigDecimal score1 = result.get("score1", BigDecimal.class);
            BigDecimal score2 = result.get("score2", BigDecimal.class);
            dto.setScore1(score1);
            dto.setScore2(score2);
            // Calculate grade
            if (score1 != null && score2 != null) {
                dto.setGrade(GradeCalculator.calculateGrade(score1, score2));
            } else {
                dto.setGrade(null);
            }
            Integer credit = result.get("credit", Integer.class);
            dto.setCredit(credit != null ? credit : 0);

            return dto;
        }
        return null;
    }

    @Transactional
    @Override
    public Boolean update(StudentDto studentDto) {
        try {
            // Use EntityManager to find the Student object by id
            Student student = entityManager.find(Student.class, studentDto.getId());
            if (student == null) {
                return false;
            }
            // Update the Student object
            student.setCode(studentDto.getCode());
            student.setName(studentDto.getName());
            // Use EntityManager to update the Student object
            entityManager.merge(student);
            if (studentDto.getSubjectId() != 0 && studentDto.getScore1() != null && studentDto.getScore2() != null) {
                // Use EntityManager to find the Subject object by id
                Subject subject = entityManager.find(Subject.class, studentDto.getSubjectId());
                if (subject == null) {
                    return false;
                }
                // Create a new Score object
                Score score = new Score();
                score.setSubject(subject);
                score.setStudent(student);
                score.setScore1(studentDto.getScore1());
                score.setScore2(studentDto.getScore2());
                // Use EntityManager to save the Score object
                entityManager.persist(score);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
