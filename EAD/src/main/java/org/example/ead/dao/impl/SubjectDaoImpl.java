package org.example.ead.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.ead.dao.SubjectDao;
import org.example.ead.dto.SubjectDto;
import org.example.ead.model.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SubjectDaoImpl implements SubjectDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SubjectDto> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<Subject> root = query.from(Subject.class);
        query.multiselect(
                root.get("id").alias("id"),
                root.get("code").alias("code"),
                root.get("name").alias("name"),
                root.get("credit").alias("credit")
        );
        query.distinct(true);
        List<Tuple> results = entityManager.createQuery(query).getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.stream()
                .map(result -> {
                    SubjectDto dto = new SubjectDto();
                    dto.setId(result.get("id", Integer.class));
                    dto.setCode(result.get("code", String.class));
                    dto.setName(result.get("name", String.class));
                    dto.setCredit(result.get("credit", Integer.class));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public Subject findById(int id) {
        try {
            return entityManager.find(Subject.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
