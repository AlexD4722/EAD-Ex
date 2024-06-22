package org.example.ead.util;

import java.math.BigDecimal;

public class GradeCalculator {
    public static Grade calculateGrade(BigDecimal score1, BigDecimal score2) {
        BigDecimal totalScore = score1.multiply(new BigDecimal("0.3")).add(score2.multiply(new BigDecimal("0.7")));
        if (totalScore.compareTo(new BigDecimal("8.0")) >= 0) {
            return Grade.A;
        } else if (totalScore.compareTo(new BigDecimal("6.0")) >= 0) {
            return Grade.B;
        } else if (totalScore.compareTo(new BigDecimal("4.0")) >= 0) {
            return Grade.C;
        } else {
            return Grade.F;
        }
    }
}
