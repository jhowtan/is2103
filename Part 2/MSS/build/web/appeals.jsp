<%-- 
    Document   : appeals
    Created on : Oct 28, 2014, 12:26:59 AM
    Author     : jon
--%>

<%@page import="java.util.Vector"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <nav class="page-header">
        <ul class="nav nav-pills" role="tablist">
            <li><a href="index">Main</a></li>
            <li><a href="profile">Profile</a></li>
            <li><a href="modules">Modules</a></li>
            <li><a href="tutorials">Tutorials</a></li>
            <li><a href="timetable">Timetable</a></li>
            <li class="active"><a href="appeals">Appeals</a></li>
            <li><a href="logout">Logout</a></li>
        </ul>
    </nav>
    <body style="background-image: url('<%=request.getContextPath()%>/school.png');">
        <div>
            <% List<Vector> appeals = (List<Vector>) request.getAttribute("data"); %> 
            <div class="jumbotron" style="width: 80%; margin-left: auto; margin-right: auto; padding: 60px;">
                <h3>My Appeals</h3>
                <% if (appeals.isEmpty()) { %>
                <hr>
                <p>You currently have no appeals in the system.</p>
                <% } else { %>
                <table class="table">
                    <tr>
                        <th>Time</th>
                        <th>Status</th>
                        <th>Content</th>
                        <th>Comments</th>
                    </tr>
                    <%  for (Vector a : appeals) {%>
                    <tr>
                        <th><%=(String) a.get(0)%></th>
                        <th><%=(String) a.get(1)%></th>
                        <th><%=(String) a.get(2)%></th>
                        <th><%=(String) a.get(3)%></th>
                    </tr>       
                    <%}%>
                </table>
                <%}%>
            </div>
        </div>
    </body>
</html>
