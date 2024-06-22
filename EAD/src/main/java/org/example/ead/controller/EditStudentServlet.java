package org.example.ead.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ead.dao.ScoreDao;
import org.example.ead.dao.StudentDao;
import org.example.ead.dao.SubjectDao;
import org.example.ead.dto.CreateStudentDto;
import org.example.ead.dto.StudentDto;
import org.example.ead.dto.StudentDtoBasic;
import org.example.ead.dto.SubjectDto;
import org.example.ead.model.Student;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "EditStudentServlet", urlPatterns = "/edit-student")
public class EditStudentServlet extends HttpServlet {
    private StudentDao studentDao;
    private SubjectDao subjectDao;
    private ScoreDao scoreDao;

    public void init() {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        studentDao = context.getBean("studentDao", StudentDao.class);
        subjectDao = context.getBean("subjectDao", SubjectDao.class);
        scoreDao = context.getBean("scoreDao", ScoreDao.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int studentId = Integer.parseInt(req.getParameter("id"));
        int subjectId = Integer.parseInt(req.getParameter("subjectId"));
        StudentDto student = studentDao.findByIdDto(studentId, subjectId);
        List<SubjectDto> subjects = subjectDao.findAll();
        req.setAttribute("subjects", subjects);
        req.setAttribute("student", student);
        req.getRequestDispatcher("/views/edit-student.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(Integer.parseInt(req.getParameter("studentId")));
        studentDto.setCode(req.getParameter("studentCode"));
        studentDto.setName(req.getParameter("studentName"));
        studentDto.setSubjectId(Integer.parseInt(req.getParameter("subjectId")));
        String score1Str = req.getParameter("score1");
        if (score1Str != null) {
            studentDto.setScore1(new BigDecimal(score1Str));
        }
        String score2Str = req.getParameter("score2");
        if (score2Str != null) {
            studentDto.setScore2(new BigDecimal(score2Str));
        }
        Boolean isUpdated = studentDao.update(studentDto);
        if (isUpdated) {
            resp.sendRedirect("student-servlet");
        } else {
            req.setAttribute("error", "Unable to update student");
            resp.sendRedirect("student-servlet");
        }
    }
}