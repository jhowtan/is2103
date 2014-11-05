<%-- 
    Document   : login
    Created on : Oct 26, 2014, 11:24:33 PM
    Author     : jon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Module Search System</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">       
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <title>Module Search System</title>
    </head>
    <body style="background-image: url('<%=request.getContextPath()%>/school.png');">
        <div style="margin-top: 100px;">
            <div class="jumbotron" style="width: 60%; margin-left: auto; margin-right: auto; padding: 50px;">
                <header class="page-header">
                    <h3>Login to Module Search System</h3>             
                </header>
                <%
                    String message = (String) request.getAttribute("message");
                    if (message != null) {
                %>
                <h4 style="color: red"><%= message%></h4>
                <% }%>
                <div class="well">

                    <form action="loginUser" method="POST" accept-charset="UTF-8"/>
                    <h4>Matric Number:</h4>
                    <input type="text" required name="matricNum" min="0" length="30"/>
                    <h4>Password:</h4>
                    <input type="password" required name="password" length="30"/>
                    <br>
                    <br>
                    <input class="btn btn-success" type="submit">
                    </form>     
                </div>
            </div>      
        </div>

    </body>
</html>