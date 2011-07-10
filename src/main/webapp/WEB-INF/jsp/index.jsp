<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/screen.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/print.css" />
    </head>

    <body>
        <div class = "container">
            <div class = "span-24 last">
                <div class = "span-14">
                    <h1> Mini Twitter </h1>
                </div>
                <div class = "span-9 last">
                    <form action = "/login" method = "POST">
                        <div class = "span-4">
                            <input type = "email" placeholder = "Email" name = "email" class = "span-4" />
                        </div>
                        <div class = "span-4 last">
                            <input type = "password" placeholder = "Password" name = "password" class = "span-4" />
                        </div>
                        <input type = "submit" value = "Login" />
                    </form>
                    <p>${loginMsg}</p>
                </div>
            </div>

            <div class = "span-14">
                <h1>Image here</h1>
            </div>
            <div class = "span-9 last">
                <h3>
                    Not a user? <em>Sign up here</em>
                </h3>
                <div class = "span-9 last">
                    <h5>${signupMsg}</h5>
                    <form action = "/signup" method = "POST">
                        <div class = "span-9 last">
                            <input type = "text" placeholder = "Full Name" name = "name" class = "span-5" />
                        </div>
                        <div class = "span-9 last">
                            <input type = "email" placeholder = "Email" name = "email" class = "span-5" />
                        </div>
                        <div class = "span-9 last">
                            <input type = "password" placeholder = "Password" name = "password" class = "span-5" />
                        </div>
                        <div class = "span-9 last">
                            <input type = "password" placeholder = "Confirm Password" name = "cpassword" class = "span-5" />
                        </div>
                        <div class = "span-9 last">
                            <input type = "submit" value = "Signup" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>