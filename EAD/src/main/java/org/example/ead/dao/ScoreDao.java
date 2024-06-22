package org.example.ead.dao;

import org.example.ead.dto.CreateScoreDto;

public interface ScoreDao {
    Boolean saveScore(CreateScoreDto createScoreDto);
}
