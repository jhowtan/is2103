<%-- 
    Document   : modResults
    Created on : Oct 27, 2014, 5:21:53 PM
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
                <li class="active"><a href="modules">Modules</a></li>
                <li><a href="tutorials">Tutorials</a></li>
                <li><a href="timetable">Timetable</a></li>
                <li><a href="appeals">Appeals</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
        <div>
            <div class="jumbotron" style="width: 80%; margin-left: auto; margin-right: auto; padding: 20px;">
                <h3>Module Search Results</h3>
                <hr>
                <%
                    List data = (List) request.getAttribute("data");
                    String modTitle = "";
                    String modCode = "";
                    String modTime = "";
                    String modVenue = "";
                    String modSynopsis = "";
                    String modLecturer = "";

                    if (!data.isEmpty()) {
                        int count = 0;
                        for (Object o : data) {
                            count++;
                            Vector moduleData = (Vector) o;
                            modTitle = (String) moduleData.get(1);
                            modCode = (String) moduleData.get(2);
                            modTime = (String) moduleData.get(3);
                            modVenue = (String) moduleData.get(4);
                            modSynopsis = (String) moduleData.get(5);
                            modLecturer = (String) moduleData.get(6);
                %>
                <!-- MODULES -->
                <div class="panel panel-default" id="panelMod<%= count%>">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-target="#collapseMod<%= count%>" 
                               href="#collapseMod<%= count%>" class="collapsed">
                                <b><%= modCode%></b> - <%= modTitle%>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseMod<%= count%>" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td rowspan="3" width="65%">
                                        <em><%= modSynopsis%></em>
                                    </td>
                                    <td>
                                        <strong>Lecturer:</strong> <%= modLecturer%>
                                    </td>
                                </tr>
                                <tr><td>
                                        <strong>Venue:</strong> <%= modVenue%>
                                    </td></tr>
                                <tr> <td>
                                        <strong>Time:</strong> <%= modTime%>
                                    </td></tr>
                            </table>
                            <!-- TUTORIALS -->
                            <div class="panel panel-default" id="panelTut<%= count%>">
                                <div class="panel-heading">
                                    <h6 class="panel-title">
                                        <!-- <a data-toggle="dropdown" data-target="#tut<!%= count%>" 
                                           href="#dropdownTut<!%= count%>">
                                            Tutorial Groups
                                        </a> -->
                                        Tutorial Groups
                                    </h6>
                                </div>
                                <div id="tut<%= count%>">
                                    <div class="panel-body">
                                        <ul>
                                            <%
                                                List tutDatas = (List) moduleData.get(9);
                                                Collections.sort(tutDatas);
                                                for (Object obj : tutDatas) {
                                                    String tutData = (String) obj;
                                                    // System.out.println("Tutorial: " + tutData);
                                            %>
                                            <li><%= tutData%></li>
                                                <%
                                                }
                                                    if (tutDatas.isEmpty()) {
                                                %>
                                            <em>There are no tutorials for this module</em>
                                            <% }%>
                                    </div>
                                </div>
                            </div>
                            <form class="form-horizontal" action="registerModule" method="POST">
                                <input type="hidden" name="modCode" value="<%= modCode%>">
                                <button class="btn btn-warning pull-right" type="submit">Register Module</button>
                            </form>
                        </div>
                    </div>
                </div>
                <%
                    }
                    if (data.isEmpty()) {
                        String query = (String) request.getAttribute("keyword");
                        if (query != null) {
                %>
                <h5><em>0 results found for "<%= query%>".</em></h5>
                <% }
                    }
                     }%>
            </div>
        </div>
    </body>
</html>
