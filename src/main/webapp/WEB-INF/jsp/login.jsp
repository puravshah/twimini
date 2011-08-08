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

            <div class = "prepend-6 span-12 append-6 add-margin-above-20 center-text">
                <div class = "span-12 rounded-box last">
                    <div class = "span-12 last">
                        <h3>Twimini Login</h3>
                        <hr>
                    </div>

                    <div id = "error-box" class = "error-box prepend-1 span-10 append-1 last add-margin-above-20">
                        <div id = "error-content" class = "error-content prepend-1 span-8 append-1 last">
                            <h3> ${msg} </h3>
                        </div>
                    </div>
                    <script type = "text/javascript">
                        if("${msg}" == "") {
                            dojo.style("error-box", "display", "none");
                        }
                    </script>

                    <div class = "prepend-2 span-8 append-2 last add-margin-above-20">
                        <form id = "form-container" action = "/login" method = "POST" onsubmit="return checkLoginIfEmpty();">
                            <div class = "span-8 last">
                                <input type = "email" placeholder = "Email" id="email" name = "email" class = "span-7" />
                            </div>
                                <div class = "span-8 last">
                                    <input type = "password" placeholder = "Password" id="password" name = "password" class = "span-7" />
                                </div>
                            <div class = "span-7 append-1 last">
                                <div class = "span-3">
                                    <input type = "submit" value = "Login" />
                                </div>
                                <div class = "span-4 last add-margin-above-10">
                                    <a href = "/forgot">Forgot Password?</a>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class = "span-5 last error-container add-margin-above-10">
                        <div id="emailMsg" class = "span-5 error-msg">${emailMsg}</div>
                        <div id="passwordMsg" class = "span-5 error-msg">${passwordMsg}</div>
                    </div>

                    <div id = "compulsory-notifier" class = "prepend-3 span-6 append- last">
                        <strong>* All fields are compulsory</strong>
                    </div>

                    <div class = "span-12 last add-margin-above-20">
                        <h4>
                            New user?
                            <a href = "/signup">Sign up</a>
                        </h4>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
