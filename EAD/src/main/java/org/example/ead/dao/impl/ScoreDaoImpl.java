package org.example.ead.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.ead.dao.ScoreDao;
import org.example.ead.dao.StudentDao;
import org.example.ead.dao.SubjectDao;
import org.example.ead.dto.CreateScoreDto;
import org.example.ead.dto.SubjectDto;
import org.example.ead.model.Score;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Repository
public class ScoreDaoImpl implements ScoreDao {
    @PersistenceContext
    private EntityManager entityManager;
    private final SubjectDao subjectDao;
    private final StudentDao studentDao;
    public ScoreDaoImpl(@Qualifier("studentDaoImpl") StudentDao studentDao,@Qualifier("subjectDaoImpl") SubjectDao subjectDao) {
        this.studentDao = studentDao;
        this.subjectDao = subjectDao;
    }
    @Transactional
    @Override
    public Boolean saveScore(CreateScoreDto createScoreDto) {
        // Convert CreateScoreDto to Score
        Score score = new Score();
        score.setScore1(createScoreDto.getScore1());
        score.setScore2(createScoreDto.getScore2());
        try {
            score.setStudent(studentDao.findById(createScoreDto.getStudentId()));
            score.setSubject(subjectDao.findById(createScoreDto.getSubjectId()));
            entityManager.persist(score);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
