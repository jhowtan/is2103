<%-- 
    Document   : profile
    Created on : Oct 25, 2014, 10:58:00 PM
    Author     : jon
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">       
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <title>Module Search System</title>
    </head>
    <body style="background-image: url('<%=request.getContextPath()%>/school.png');">

        <div>
            <%
                ArrayList<String> studentInfo = (ArrayList) request.getAttribute("studentInfo");
                String name = "name";
                String email = "email";
                String matricNum = "matric";
                if (studentInfo != null) {
                    matricNum = (String) studentInfo.get(0);
                    name = (String) studentInfo.get(1);
                    email = (String) studentInfo.get(3);
                }
                String message = (String) request.getAttribute("message");
            %>
            <div class="page-header">
                <ul class="nav nav-pills">
                    <li><a href="index">Main</a></li>
                    <li class="active"><a href="profile">Profile</a></li>
                    <li><a href="modules">Modules</a></li>
                    <li><a href="tutorials">Tutorials</a></li>
                    <li><a href="timetable">Timetable</a></li>
                    <li><a href="appeals">Appeals</a></li>
                    <li><a href="logout">Logout</a></li>
                </ul>
            </div>
            <div class="jumbotron" style="width: 70%; margin-left: auto; margin-right: auto; padding: 50px;">
                <form name="email" class="form-horizontal" action="updateEmail" method="POST">
                    <h1>Welcome, <%= name%>! </h1>
                    <h3><small>Your Matriculation Number is: </small><em><%= matricNum%></em></h3>
                    <div><strong>Update Email</strong></div>
                    <div class="panel-body">
                        <div class="input-group">
                            <span class="input-group-addon">
                                Email
                            </span>
                            <input type="text" class="form-control" name="email" placeholder="<%= email%>" required>
                        </div>
                        <br>
                        <div class="navbar-left">
                            <button class="btn btn-success" type="submit" value="submit">Update Email</button>
                        </div>
                    </div>

                </form>
                <form name="password" class="form-horizontal" action="updatePassword" method="POST">
                    <div><strong>Update Password</strong></div>
                    <div class="panel-body">
                        <div class="input-group">
                            <span class="input-group-addon">

                                Current Password
                            </span>
                            <input type="password" class="form-control" name="oldPwd" placeholder="" required>
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">
                                New Password
                            </span>
                            <input type="password" class="form-control" name="newPwd" placeholder="" required>
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">
                                Confirm Password
                            </span>
                            <input type="password" class="form-control" name="cfmPwd" placeholder="" required>
                        </div>
                        <br>
                        <div class="navbar-left">
                            <button class="btn btn-success" type="submit" value="submit">Change Password</button>
                        </div>
                        <br><br>
                        <div>
                            <% if (message != null) {
                            %>
                            <h4><%= message%></h4>
                            <% }%>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
