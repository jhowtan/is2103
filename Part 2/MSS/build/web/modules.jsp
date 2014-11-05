<%-- 
    Document   : module
    Created on : Oct 26, 2014, 2:04:34 PM
    Author     : jon
--%>

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
    <body style="background-image: url('<%=request.getContextPath()%>/school.png');">

        <div class="page-header">
            <ul class="nav nav-pills">
                <li><a href="index">Main</a></li>
                <li><a href="profile">Profile</a></li>
                <li class="active"><a href="modules">Modules</a></li>
                <li><a href="tutorials">Tutorials</a></li>
                <li><a href="timetable">Timetable</a></li>
                <li><a href="appeals">Appeals</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
        <div>
            <div class="jumbotron" style="width: 80%; margin-left: auto; margin-right: auto; padding: 100px;">
                <h3>Search for Modules:</h3>
                <p>
                    <%
                        String message = (String) request.getAttribute("message");
                        if (message == null) {
                            message = "Please enter a keyword.";
                        }
                    %>
                    <em> <%= message%> </em>
                </p>
                <form action="search" class="navbar-form navbar-left" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" name="keyword" placeholder="e.g. Module Title">
                    </div>
                    <button type="submit" class="btn btn-success">Search</button>
                </form>
                <br><br>
            </div>

    </body>
</html>