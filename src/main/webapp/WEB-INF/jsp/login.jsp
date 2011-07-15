<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/screen.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/print.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/style.css" />
    </head>

    <body>
        <div class = "container" style = "padding-left: 100px;">
            <div class = "span-24 last header">
                <div id = "twitter-logo" class = "span-16">
                    <img src = "/static/images/logo.png" alt = "Mini Twitter" />
                </div>

                <div id = "login-text" class = "span-5 login-text">
                    <span>New User? <b>Sign up!</b></span>
                </div>
            </div>

            <div id = "body-container" class = "span-24 last push-5">
                <div id = "error-box" class = "span-24 last">
                    <h3> ${msg} </h3>
                </div>

                <div class = "span-14 last">
                    <form id = "form-container" action = "/login" method = "POST">
                        <div class = "span-14 last">
                            <input type = "text" placeholder = "Email" name = "email" class = "span-7" />
                        </div>
                            <div class = "span-14 last">
                                <input type = "password" placeholder = "Password" name = "password" class = "span-7" />
                            </div>
                        <div class = "span-14 last">
                            <input type = "submit" value = "Login" />
                        </div>
                    </form>
                </div>

                <div class = span-14 last>
                    <h4>
                        New user?
                        <a href = "/signup">Sign up</a>
                    </h4>
                </div>
            </div>
        </div>
    </body>
</html>
