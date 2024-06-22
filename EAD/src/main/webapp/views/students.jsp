<%@ page import="org.example.ead.dto.StudentDto" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Students List</title>
    <!-- Add Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<div class="container">
    <div class="container flex gap-10">
        <a href="add-student" class="btn btn-primary">
            <i class="fas fa-plus"></i>  Student
        </a>
        <a href="add-score" class="btn btn-primary">
            <i class="fas fa-plus"></i>  Score
        </a>
    </div>
    <h2>Students List</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Code</th>
            <th>Name</th>
            <th>Subject Name</th>
            <th>Score 1</th>
            <th>Score 2</th>
            <th>Credit</th>
            <th>Grade</th>
        </tr>
        </thead>
        <tbody>
        <!-- Use JSTL to iterate over the students list -->
        <%
            List<StudentDto> students = (List<StudentDto>) request.getAttribute("students");
            int count = 1;
            for (StudentDto student : students) {
        %>
        <tr>
            <td><%= count++ %></td>
            <td><%= student.getCode() != null ? student.getCode() : "" %></td>
            <td><%= student.getName() != null ? student.getName() : "" %></td>
            <td><%= student.getSubjectName() != null ? student.getSubjectName() : "" %></td>
            <td><%= student.getScore1() != null ? student.getScore1() : "" %></td>
            <td><%= student.getScore2() != null ? student.getScore2() : "" %></td>
            <td><%= student.getCredit() != 0 ? student.getCredit() : "" %></td>
            <td><%= student.getGrade() != null ? student.getGrade() : "" %></td>
            <td>
                <!-- Edit button with icon -->
                <a href="edit-student" class="btn btn-primary">
                    <i class="fas fa-edit"></i>
                </a>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>