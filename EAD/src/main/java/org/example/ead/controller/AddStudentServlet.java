package org.example.ead.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ead.dao.StudentDao;
import org.example.ead.dto.CreateStudentDto;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

@WebServlet(name = "AddStudentServlet", urlPatterns = "/add-student")
public class AddStudentServlet extends HttpServlet {
    private StudentDao studentDao;

    public void init() {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        studentDao = context.getBean("studentDao", StudentDao.class);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/add-student.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateStudentDto createStudentDto = new CreateStudentDto();
        createStudentDto.setCode(req.getParameter("code"));
        createStudentDto.setName(req.getParameter("name"));
        createStudentDto.setAddress(req.getParameter("address"));

        if (studentDao.save(createStudentDto)) {
            resp.sendRedirect("student-servlet"); // Redirect to students list after successful addition
        } else {
            req.setAttribute("error", "Unable to add student");
            req.getRequestDispatcher("/views/add-student.jsp").forward(req, resp);
        }
    }
}