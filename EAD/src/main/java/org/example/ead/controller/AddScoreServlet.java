package org.example.ead.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ead.dao.ScoreDao;
import org.example.ead.dao.StudentDao;
import org.example.ead.dao.SubjectDao;
import org.example.ead.dto.CreateScoreDto;
import org.example.ead.dto.StudentDtoBasic;
import org.example.ead.dto.SubjectDto;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "AddScoreServlet", urlPatterns = "/add-score")
public class AddScoreServlet extends HttpServlet {
        private ScoreDao scoreDao;
    private SubjectDao subjectDao;
    private StudentDao studentDao;

    public void init() {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        subjectDao = context.getBean("subjectDao", SubjectDao.class);
        studentDao = context.getBean("studentDao", StudentDao.class);
        scoreDao = context.getBean("scoreDao", ScoreDao.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<SubjectDto> subjects = subjectDao.findAll();
        List<StudentDtoBasic> students = studentDao.findAllDtoBasic();
        req.setAttribute("subjects", subjects);
        req.setAttribute("students", students);
        req.getRequestDispatcher("/views/add-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateScoreDto createScoreDto = new CreateScoreDto();
        createScoreDto.setStudentId(Integer.parseInt(req.getParameter("studentId")));
        createScoreDto.setSubjectId(Integer.parseInt(req.getParameter("subjectId")));
        createScoreDto.setScore1(new BigDecimal(req.getParameter("score1")));
        createScoreDto.setScore2(new BigDecimal(req.getParameter("score2")));
        if (scoreDao.saveScore(createScoreDto)) {
            resp.sendRedirect("student-servlet"); // Redirect to scores list after successful addition
        } else {
            req.setAttribute("error", "Unable to add score");
            req.getRequestDispatcher("/views/add-score.jsp").forward(req, resp);
        }
    }
}