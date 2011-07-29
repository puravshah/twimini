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

            <div id = "body-container" class = "span-24 last">
                <div id = "signup-left" class = "span-6">
                    <h1>New to Twitter?</h1><br />
                    <h3>Create a new <br />account here.</h3>
                </div>
                <div class = "span-17 last">
                    <div id = "error-box">
                        <h3>${msg}</h3>
                    </div>

                    <div class = "span-8 last">
                        <form id = "form-container" action = "/signup" method = "POST">
                            <div class = "span-8 last">
                                <input type = "text" placeholder = "Full Name" name = "name" value = "${name}" class = "span-7" />
                            </div>
                            <div class = "span-8 last">
                                <input type = "email" placeholder = "Email" name = "email" value = "${email}" class = "span-7" />
                            </div>
                            <div class = "span-8 last">
                                <input type = "password" placeholder = "Password" name = "password" class = "span-7" />
                            </div>
                            <div class = "span-8 last">
                                <input type = "password" placeholder = "Confirm Password" name = "cpassword" class = "span-7" />
                            </div>
                            <div class = "span-8 last">
                                <input type = "submit" value = "Create Account" class = "span-7" />
                            </div>
                        </form>
                    </div>

                    <div class = "span-5 last error-container">
                        <div class = "span-5 error-msg">${nameMsg}</div>
                        <div class = "span-5 error-msg">${emailMsg}</div>
                        <div class = "span-5 error-msg">${passwordMsg}</div>
                        <div class = "span-5 error-msg">${cpasswordMsg}</div>
                    </div>

                    <div id = "compulsory-notifier" class = "span-17 last">
                        <strong>* All fields are compulsory</strong>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
