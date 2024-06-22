package org.example.ead.dao;

import org.example.ead.dto.CreateStudentDto;
import org.example.ead.dto.StudentDto;
import org.example.ead.dto.StudentDtoBasic;
import org.example.ead.model.Student;

import java.util.List;

public interface StudentDao {
    List<StudentDto> findAllDto();
    List<StudentDtoBasic> findAllDtoBasic();
    Boolean save(CreateStudentDto CreateStudentDto);
    Student findById(int id);
}
