package org.example.ead.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ead.dao.StudentDao;
import org.example.ead.dto.StudentDto;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "studentServlet", value = "/student-servlet")
public class GetStudentServlet extends HttpServlet {
    private StudentDao studentDao;

    public void init() {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        studentDao = context.getBean("studentDao", StudentDao.class);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<StudentDto> students = studentDao.findAllDto();
        request.setAttribute("students", students); // set students in request scope
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/students.jsp"); // path to your JSP file
        dispatcher.forward(request, response);
    }

    public void destroy() {
    }
}