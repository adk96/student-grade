<%@ page import="entity.Student" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.DatabaseDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8">
        <title>fSchool</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" href="css/ShowAllStudent.css">
        <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="css/menu.css">
        <script src="js/jquery1111.min.js" type="text/javascript"></script>
        <script src="js/script.js"></script>
    </head>

    <body class="home-page">
        <div class="wrap-body">
            <header>
                <div id="cssmenu">
                    <ul>
                        <li class="active"><a href="index.html"><span>FSchool</span></a></li>
                        <li class="has-sub"><a href="#"><span>Students</span></a>
                               <ul>
                                <li class="has-sub"><a href="MyServlet?actionname=ShowAllStudent"><span>Show All Students</span></a>
                                    
                                   <li><a href="addstudent.jsp"><span>Add</span></a></li>
                                 <li><a href="RemoveStudent.jsp"><span>Remove</span></a></li>
                                    
                                
                            </ul>
                        </li>
                        <li><a href="archive.html"><span>Media</span></a></li>
                        <li><a href="single.html"><span>Skills</span></a></li>
                        <li class="last"><a href="contact.html"><span>About</span></a></li>
                    </ul>
                </div>
            </header>

            <div class="ShowAllStudent">
                <h1>All Students</h1>
                <div>
                    <br>
                    <% String message = (String) request.getAttribute("message");%>
                    <%=message == null ? "" : message%>
                    <br><br><br>
                </div>
                <table border="1">
                    <thead>
                        <tr>
                            <td>Image</td>
                            <td>Surname</td>
                            <td>Name</td>
                            <td>Delete</td>
                        </tr>
                    </thead>
                    <%
                        ArrayList<Student> students = (ArrayList<Student>) request.getAttribute("students");
                        for (Student student : students) {
                    %>
                    <tr>
                        <td>
                            <img src="GetImage?id=<%=student.getId()%>" width="50">
                        </td>
                        <td><%=student.getSurname()%></td>
                        <td><%=student.getName()%></td>
                        <td align="center">
                            <a href="MyServlet?actionname=RemoveStudent&id=<%=student.getId()%>">X</a>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
        </div>
    </body>
</html>
