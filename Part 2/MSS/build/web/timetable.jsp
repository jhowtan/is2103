<%-- 
    Document   : timetable
    Created on : Oct 27, 2014, 10:57:25 PM
    Author     : jon
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,
        java.util.Vector,
        java.util.Collections" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">       
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <title>Module Search System</title>
    </head>
    <body style="background-image: url('<%=request.getContextPath()%>/school.png');">

        <div class="page-header">
            <ul class="nav nav-pills">
                <li><a href="index">Main</a></li>
                <li><a href="profile">Profile</a></li>
                <li><a href="modules">Modules</a></li>
                <li><a href="tutorials">Tutorials</a></li>
                <li class="active"><a href="timetable">Timetable</a></li>
                <li><a href="appeals">Appeals</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
        <div>
            <div class="jumbotron" style="width: 80%; margin-left: auto; margin-right: auto; padding: 60px;">

                <%
                    ArrayList<String> lessons = (ArrayList<String>) request.getAttribute("data");
                    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
                %>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3>My Timetable</h3>   
                    </div>
                    <div class="panel-body">
                        <table class="table">
                            <%  int count;
                                for (String day : days) {
                            %>
                            <tr>
                                <td width = "65%"> 
                                    <em> <%= day%></em>
                                </td>
                                <td><table class="table">
                                        <% for (String lesson : lessons) {
                                                if (lesson.split(" ")[0].equals(day)) {
                                        %>
                                        <tr>
                                            <td><strong><%= lesson.split(" ")[3]%>:</strong> <%= lesson.split(" ")[1]%> - <%= lesson.split(" ")[2]%> </td>

                                        </tr>
                                        <% }
                                            } %></table> </td> <%
                                    }%>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
