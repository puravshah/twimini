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
                <div class = "span-14">
                    <img src = "/static/images/logo.png" alt = "Mini Twitter" />
                </div>
                <div class = "span-10 last add-padding-above-5">
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
                    <p>${loginMsg}</p>
                </div>
            </div>

            <div class = "span-24 last lowerbody add-padding-above-20">
                <div class = "span-14">
                    <img src = "/static/images/" height = "250" width = "550" alt = "image here" />
                </div>

                <div class = "span-7 last">
                    <h3>
                        Not a user? <em>Sign up here</em>
                    </h3>
                    <hr>
                    <div class = "span-7 last">
                        <h5>${signupMsg}</h5>
                        <form action = "/signup" method = "POST">
                            <div class = "span-7 last">
                                <input type = "text" placeholder = "Full Name" name = "name" class = "span-7" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "email" placeholder = "Email" name = "email" class = "span-7" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "password" placeholder = "Password" name = "password" class = "span-7" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "password" placeholder = "Confirm Password" name = "cpassword" class = "span-7" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "submit" value = "Signup" />
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>