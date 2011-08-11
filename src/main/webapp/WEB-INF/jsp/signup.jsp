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
                    <a href = "/"><img src = "/static/images/logo.png" alt = "Mini Twitter" /></a>
                </div>

                <div id = "login-text" class = "span-5 append-3 last" onclick = "toggleLoginDropdown();">
                    <span>Already a memeber? <b>Sign in</b><img src = "/static/images/icon_dropdown_1.png"/></span>
                </div>

                <div id = "login-dropdown" class = "span-5">
                    <form action = "/login" method = "POST">
                        <div class = "span-4">
                            <input type = "email" placeholder = "Email" name = "email" class = "span-4" maxlength="40"/>
                        </div>
                        <div class = "span-4">
                            <input type = "password" placeholder = "Password" name = "password" class = "span-4" maxlength="40" />
                        </div>
                        <div class = "span-5 last add-padding-above-5">
                            <div class = "span-1">
                                <input type = "submit" value = "Login" />
                            </div>
                            <div class = "span-4 last center-text add-margin-above-5">
                                <a href = "/forgot">Forgot Password?</a>
                            </div>
                        </div>
                    </form>
                </div>
                <script type = "text/javascript">
                    var parentInfo = dojo.position('login-text', true);
                    var hiddenChild = dojo.byId('login-dropdown');
                    var hiddenChildInfo = dojo.position('login-dropdown', true);

                    var left = parentInfo.x + parentInfo.w - hiddenChildInfo.w;
                    var bottom = parentInfo.y + parentInfo.h;
                    //alert(parentInfo.x + ", " + left + ", " + parentInfo.w + ", " + hiddenChildInfo.w);

                    dojo.style(hiddenChild, {
                        left: left + "px",
                        top: bottom + "px"
                    });
                    dojo.style("login-dropdown", "display", "none");
                </script>
            </div>

            <div class = "prepend-5 span-14 append-5 last add-margin-above-20">
            <%--<div id = "body-container" class = "span-24 last">
                <div id = "signup-left" class = "span-6">
                    <h1>New to Twitter?</h1><br />
                    <h3>Create a new <br />account here.</h3>
                </div>--%>
                <div class = "rounded-box span-14 last">
                    <div class = "span-14 center-text last">
                        <h3>Twimini Signup</h3>
                        <hr>
                    </div>

                    <div id = "error-box" class = "error-box prepend-2 span-10 append-2 last add-margin-above-20">
                        <div id = "error-content" class = "error-content prepend-1 span-8 append-1 last">
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
                        <form id = "form-container" action = "/signup" method = "POST" onsubmit="return resetError();return isAlpha('name');return checkIfEmpty();return checkPassword();">
                            <div class = "span-8 last">
                                <input type = "text" placeholder = "Full Name" id="name" name = "name" value = "${name}" class = "span-7"/>
                            </div>
                            <div class = "span-8 last">
                                <input type = "email" placeholder = "Email" id="email" name = "email" value = "${email}" class = "span-7" />
                            </div>
                            <div class = "span-8 last">
                                <input type = "password" placeholder = "Password"  id="password" name = "password" class = "span-7" />
                            </div>
                            <div class = "span-8 last">
                                <input type = "password" placeholder = "Confirm Password" id="cpassword" name = "cpassword" class = "span-7" />
                            </div>
                            <div class = "span-8 last">
                                <input type = "submit" value = "Create Account" class = "span-7 yellow-button" />
                            </div>
                        </form>
                    </div>

                    <div class = "span-5 last error-container add-margin-above-20">
                        <div id="nameMsg" class = "span-5 error-msg">${nameMsg}</div>
                        <div id="emailMsg" class = "span-5 error-msg">${emailMsg}</div>
                        <div id="passwordMsg" class = "span-5 error-msg">${passwordMsg}</div>
                        <div id="cpasswordMsg" class = "span-5 error-msg">${cpasswordMsg}</div>
                    </div>

                    <div id = "compulsory-notifier" class = "prepend-3 span-6 append- last">
                        <strong>* All fields are compulsory</strong>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
