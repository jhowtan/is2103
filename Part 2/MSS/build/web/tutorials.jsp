<%-- 
    Document   : tutorials
    Created on : Oct 27, 2014, 12:30:36 PM
    Author     : jon
--%>

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
                <li class="active"><a href="tutorials">Tutorials</a></li>
                <li><a href="timetable">Timetable</a></li>
                <li><a href="appeals">Appeals</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
        <div>
            <div class="jumbotron" style="width: 80%; margin-left: auto; margin-right: auto; padding: 100px;">
                <h3>Tutorial Registration</h3>
                <% List<Vector> studentModules = (List<Vector>) request.getAttribute("data"); %>
                <hr>
                <% if (studentModules.isEmpty()) { %>
                <p>
                    <%
                        String message = (String) request.getAttribute("message");
                        if (message == null) {
                            message = "You are currently not registered to any modules!";
                        }
                    %>
                    <em> <%= message%> </em>
                    <% } %>
                </p>
                <%
                    for (Object o : studentModules) {
                        Vector moduleData = (Vector) o;
                        String modTitle = (String) moduleData.get(0);
                        String modCode = (String) moduleData.get(1);
                        String modTime = (String) moduleData.get(2);
                        String modVenue = (String) moduleData.get(3);
                        String modSynopsis = (String) moduleData.get(4);
                        String modLecturer = (String) moduleData.get(5);
                        List<String> tutorialDes = (List) moduleData.get(6);
                %>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><%= modCode%>: <%= modTitle%></h4>
                    </div>
                    <div class="panel-body">
                        <table class="table">
                            <tr>
                                <td rowspan="3" width="65%">
                                    <em><%= modSynopsis%></em>
                                </td>
                                <td>
                                    <b>Lecturer:</b> <%= modLecturer%>
                                </td>
                            </tr>
                            <tr><td>
                                    <b>Lecture venue:</b> <%= modVenue%>
                                </td></tr>
                            <tr> <td>
                                    <b>Lecture time:</b> <%= modTime%>
                                </td></tr>
                        </table>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4>Tutorials</h4>
                            </div>
                            <div class="panel-body">
                                <form class="form-horizontal" action="registerTutorial" method="POST">
                                    <%
                                        for (Object obj : tutorialDes) {
                                            String tutDes = (String) obj;
                                            String tutGroup = tutDes.split(" ")[1];
                                            tutGroup = tutGroup.substring(0, tutGroup.length() - 1);
                                    %>
                                    <div>    
                                        <input type="hidden" name="modCode" value="<%=modCode%>">
                                        <input type="radio" name="tutGrp" value="<%=tutGroup%>" 
                                               <h5 style="display: inline-block; vertical-align: baseline;">
                                            <%= tutDes%>
                                        </h5>
                                    </div>
                                    <%
                                        }
                                        if (tutorialDes.isEmpty()) {
                                    %>
                                    <em>There are no tutorials for this module</em>
                                    <% } else { %>
                                    <button class="btn btn-success pull-right" type="submit">Register Tutorial</button>
                                    <% } %>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <% }%>
            </div>
        </div>
    </body>
</html>
