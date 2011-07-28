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
            <div class = "span-24 last header">
                <div id = "twitter-logo" class = "span-13">
                    <img src = "/static/images/logo.png" alt = "Mini Twitter" />
                </div>
                <div id = "login-handler" class = "span-10 last">
                    <form action = "/login" method = "POST">
                        <div class = "span-4">
                            <input type = "email" placeholder = "Email" name = "email" class = "span-4" />
                        </div>
                        <div class = "span-4">
                            <input type = "password" placeholder = "Password" name = "password" class = "span-4" />
                        </div>
                        <div class = "span-2 last add-padding-above-5">
                            <input type = "submit" value = "Login" />
                        </div>
                    </form>
                </div>
            </div>

            <div class = "span-24 last lowerbody">
                <div class = "span-14">
                    <img src = "/static/images/social-network-2.jpg" height = "325" width = "500" alt = "image here" />
                </div>

                <div class = "span-7 last">
                    <h3>
                        New user? <em>Sign up here</em>
                    </h3>
                    <hr>
                    <div class = "span-7 last">
                        <form id = "signup-form" action = "/signup" method = "POST">
                            <div class = "span-7 last">
                                <input type = "text" placeholder = "Full Name" name = "name" class = "span-7" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "email" placeholder = "Email" name = "email" class = "span-7" autocomplete="off" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "password" placeholder = "Password" name = "password" class = "span-7" autocomplete="off" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "password" placeholder = "Confirm Password" name = "cpassword" class = "span-7" />
                            </div>
                            <div class = "span-7 last add-margin-above-10">
                                <input type = "submit" value = "Signup" />
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>