package org.example.ead.dao;

import org.example.ead.dto.SubjectDto;
import org.example.ead.model.Subject;

import java.util.List;

public interface SubjectDao {
    List<SubjectDto> findAll();
    Subject findById(int id);
}
