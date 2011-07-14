<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/screen.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/print.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/style.css" />
        <script type = "text/javascript" src = "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type = "text/javascript" src = "/static/js/ejs_production.js"></script>
        <script type = "text/javascript" src = "/static/js/external_js_file.js"></script>
    </head>

    <body>
        <div class = "container">
            <div class = "span-24 last header">
                <div id = "twitter-logo" class = "span-16">
                    <img src = "/static/images/logo.png" alt = "Mini Twitter" />
                </div>

                <div id = "login-text" class = "span-5" onclick = "toggleLoginDropdown();">
                    <span>Already a memeber? <b>Sign in</b><img src = "/static/images/icon_dropdown_1.png"/></span>
                </div>

                <div id = "login-dropdown" class = "span-4 last">
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
                <script type = "text/javascript">
                    $('#login-dropdown').hide();
                </script>
            </div>

            <div id = "signup-left" class = "span-5">
                <h1>New to Twitter?</h1>
                <h3>Create a new account here.</h3>
            </div>
            <div class = "span-19 last">
                <div id = "signup-errors">
                    <h3>${msg}</h3>
                </div>

                <div class = "span-19">
                    <form id = "signupForm" action = "/signup" method = "POST">
                        <div class = "span-10 last">
                            <input type = "text" placeholder = "Full Name" name = "name" /></td>
                        </div>
                        <div class = "span-10 last">
                            <input type = "text" placeholder = "Email" name = "email" /></td>
                        </div>
                        <div class = "span-10 last">
                            <input type = "password" placeholder = "Password" name = "password" /></td>
                        </div>
                        <div class = "span-10 last">
                            <input type = "password" placeholder = "Confirm Password" name = "cpassword" /></td>
                        </div>
                        <div class = "span-10 last">
                            <input type = "submit" value = "Signup" class = "span-3"/></td>
                            <a href = "/login">Cancel</a>
                        </div>
                    </form>
                </div>

                <div>
                    <p>All fields are compulsory</p>
                </div>
            </div>
        </div>
    </body>
</html>
