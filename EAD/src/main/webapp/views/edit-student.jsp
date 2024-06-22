<%@ page import="org.example.ead.dto.StudentDto" %>
<%@ page import="org.example.ead.dto.SubjectDto" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Student</title>
    <!-- Include Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Edit Student</h2>
    <%
        StudentDto student = (StudentDto) request.getAttribute("student");
    %>
    <form action="edit-student" method="post">
        <div class="form-group">
            <label for="studentId">Student ID:</label>
            <input type="text" class="form-control" id="studentId" name="studentId" value="<%= student.getId() %>"
                   readonly>
        </div>
        <div class="form-group">
            <label for="studentCode">Student Code:</label>
            <input type="text" class="form-control" id="studentCode" name="studentCode"
                   value="<%= student.getCode() %> " required>
        </div>
        <div class="form-group">
            <label for="studentName">Student Name:</label>
            <input type="text" class="form-control" id="studentName" name="studentName" value="<%= student.getName() %>"
                   required>
        </div>
        <select class="form-control" id="subjectSelect" name="subjectId">
            <%
                List<SubjectDto> subjects = (List<SubjectDto>) request.getAttribute("subjects");
                if (subjects != null) {
                    for (SubjectDto subject : subjects) {
                        String selected = (subject.getId() == student.getSubjectId()) ? "selected" : "";
            %>
            <option value="<%= subject.getId() %>" <%= selected %>><%= subject.getName() %>
            </option>
            <%
                    }
                }
            %>
        </select>
        <div class="form-group">
            <label for="score1">Score 1:</label>
            <input type="text" class="form-control" id="score1" name="score1" pattern="^\d*\.?\d+$" title="Please enter a positive decimal number." value="<%= student.getScore1() %>" required>
        </div>
        <div class="form-group">
            <label for="score2">Score 2:</label>
            <input type="text" class="form-control" id="score2" name="score2" pattern="^\d*\.?\d+$" title="Please enter a positive decimal number." value="<%= student.getScore2() %>" required>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>