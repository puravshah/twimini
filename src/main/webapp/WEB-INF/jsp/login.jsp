<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <%@include file="head include.txt"%>
    </head>

    <body>
        <div class = "container" style = "padding-left: 100px;">
            <div class = "span-24 last header">
                <div id = "twitter-logo" class = "span-16">
                    <img src = "/static/images/logo.png" alt = "Mini Twitter" />
                </div>

                <div id = "login-text" class = "span-4">
                    <a href = "/signup">New User? <b>Sign up!</b></a>
                </div>
            </div>

            <div class = "prepend-5 span-14 append-5 last add-margin-above-20">
                <div id = "error-box" class = "span-14 last">
                    <h3> ${msg} </h3>
                </div>

                <div class = "span-14 last">
                    <form id = "form-container" action = "/login" method = "POST">
                        <div class = "span-14 last">
                            <input type = "email" placeholder = "Email" name = "email" class = "span-7" />
                        </div>
                            <div class = "span-14 last">
                                <input type = "password" placeholder = "Password" name = "password" class = "span-7" />
                            </div>
                        <div class = "span-7 append-7 last">
                            <div class = "span-3">
                                <input type = "submit" value = "Login" />
                            </div>
                            <div class = "span-4 last add-margin-above-10">
                                <a href = "/forgot">Forgot Password?</a>
                            </div>
                        </div>
                    </form>
                </div>

                <div class = "span-14 last add-margin-above-20">
                    <h4>
                        New user?
                        <a href = "/signup">Sign up</a>
                    </h4>
                </div>
            </div>
        </div>
    </body>
</html>
