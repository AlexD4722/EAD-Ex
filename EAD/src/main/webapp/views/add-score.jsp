<%@ page import="org.example.ead.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.ead.model.Subject" %>
<%@ page import="org.example.ead.dto.StudentDtoBasic" %>
<%@ page import="org.example.ead.dto.SubjectDto" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Score</title>
    <!-- Include Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Add Score</h2>
    <form action="add-score" method="post">
        <div class="form-group">
            <label for="studentSelect">Select Student:</label>
            <select class="form-control" id="studentSelect" name="studentId">
                <%
                    List<StudentDtoBasic> students = (List<StudentDtoBasic>) request.getAttribute("students");
                    if (students != null) {
                        for (StudentDtoBasic student : students) {
                %>
                <option value="<%= student.getId() %>"><%= student.getName() %>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <div class="form-group">
            <label for="subjectSelect">Select Subject:</label>
            <select class="form-control" id="subjectSelect" name="subjectId">
                <%
                    List<SubjectDto> subjects = (List<SubjectDto>) request.getAttribute("subjects");
                    if (subjects != null) {
                        for (SubjectDto subject : subjects) {
                %>
                <option value="<%= subject.getId() %>"><%= subject.getName() %>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <div class="form-group">
            <label for="score1">Score 1:</label>
            <input type="number" class="form-control" id="score1" name="score1" required>
        </div>
        <div class="form-group">
            <label for="score2">Score 2:</label>
            <input type="number" class="form-control" id="score2" name="score2" required>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>