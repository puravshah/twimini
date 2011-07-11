<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/screen.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/print.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/style.css" />
    </head>

    <body>
        <div class = "container">
            <div>
                <h3> ${msg} </h3>
            </div>

            <div>
                <form action = "/login" method = "POST">
                    <div>
                        <input type = "text" placeholder = "Email" name = "email" />
                    </div>
                        <div>
                            <input type = "password" placeholder = "Password" name = "password" />
                        </div>
                    <div>
                        <input type = "submit" value = "Login" />
                    </div>
                </form>
            </div>

            <div>
                <h4>
                    Not a user?
                    <a href = "/signup">Sign up</a>
                </h4>
            </div>
        </div>
    </body>
</html>
