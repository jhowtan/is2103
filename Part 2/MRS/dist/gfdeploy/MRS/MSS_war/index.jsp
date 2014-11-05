<%-- 
    Document   : index
    Created on : Oct 20, 2014, 1:32:51 PM
    Author     : jon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <div style="margin-top: 100px;">
            <div class="jumbotron" style="width: 60%; margin-left: auto; margin-right: auto; padding: 50px;">
                <h1 align="center">Module Search System</h1>
                <h3 align="center"><em>Easy lookup and registration of modules and tutorials</em></h3><br>
                    <p align="center">
                        <a href="/MSS/MSS/login" class="btn btn-primary btn-lg" role="button">
                            Get Started
                        </a>
                    </p>
            </div>
        </div>
    </body>
</html>
