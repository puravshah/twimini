<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <%@include file="head include.txt"%>
    </head>

    <body>
        <div class = "container">
            <div class = "span-24 last header">
                <div id = "twitter-logo" class = "span-16">
                    <img src = "/static/images/logo.png" alt = "Mini Twitter" />
                </div>

                <div id = "login-text" class = "span-5 append-3 last" onclick = "toggleLoginDropdown();">
                    <span>Already a memeber? <b>Sign in</b><img src = "/static/images/icon_dropdown_1.png"/></span>
                </div>

                <div id = "login-dropdown" class = "span-4">
                    <form action = "/login" method = "POST">
                        <div class = "span-4">
                            <input type = "email" placeholder = "Email" name = "email" class = "span-4" />
                        </div>
                        <div class = "span-4">
                            <input type = "password" placeholder = "Password" name = "password" class = "span-4" />
                        </div>
                        <div class = "span-4 last add-padding-above-5">
                            <div>
                                <input type = "submit" value = "Login" />
                            </div>
                        </div>
                    </form>
                </div>
                <script type = "text/javascript">
                    $('#login-dropdown').hide();
                </script>
            </div>

            <div class = "prepend-5 span-14 append-5 last add-margin-above-20">
            <%--<div id = "body-container" class = "span-24 last">
                <div id = "signup-left" class = "span-6">
                    <h1>New to Twitter?</h1><br />
                    <h3>Create a new <br />account here.</h3>
                </div>--%>
                <div class = "rounded-box span-14 last">
                    <div id = "error-box" class = "prepend-2 span-10 append-2 last add-margin-above-20">
                        <div id = "error-content" class = "prepend-1 span-8 append-1 last">
                            <h3> ${msg} </h3>
                        </div>
                    </div>
                    <script type = "text/javascript">
                        if("${msg}" == "") {
                            dojo.style("error-box", "display", "none");
                            //dojo.style("compulsory-notifier", "display", "none");
                        }
                    </script>

                    <div class = "prepend-1 span-8 add-margin-above-20">
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
                                <input type = "submit" value = "Create Account" class = "span-7 yellow-button" />
                            </div>
                        </form>
                    </div>

                    <div class = "span-5 last error-container add-margin-above-20">
                        <div class = "span-5 error-msg">${nameMsg}</div>
                        <div class = "span-5 error-msg">${emailMsg}</div>
                        <div class = "span-5 error-msg">${passwordMsg}</div>
                        <div class = "span-5 error-msg">${cpasswordMsg}</div>
                    </div>

                    <div id = "compulsory-notifier" class = "prepend-3 span-6 append- last">
                        <strong>* All fields are compulsory</strong>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
