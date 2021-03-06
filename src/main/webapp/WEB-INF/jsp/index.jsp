<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <%@include file="head include.txt"%>
        <script type="text/javascript" src="/static/js/external_js_file.js"></script>
    </head>

    <body>
        <div class = "container">
            <jsp:include page = "indexHeader.jsp">
                <jsp:param name="url" value="javascript:void(0);"></jsp:param>
            </jsp:include>
            <div class = "span-24 last lowerbody">
                <div class = "span-14">
                    <img src = "/static/images/social-network-2.jpg" height = "325" width = "500" alt = "image here" />
                </div>
                <div class = "span-7 last">
                    <h3>
                        New user? <em>Sign up here</em>
                    </h3>
                    <hr>
                    <div class = "span-7 last ">
                        <form id = "signup-form" action = "/signup" method = "POST" class="formContainer" >
                            <div class = "span-7 last">
                                <input type = "text" placeholder = "Full Name" id= "name" name = "name" maxlength="40" class = "span-7"/>
                            </div>
                            <div class = "span-7 last">
                                <input type = "email" placeholder = "Email" id="email" name = "email" maxlength="40" class = "span-7" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "password" placeholder = "Password" id="password" name = "password" maxlength="40" class = "span-7" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "password" placeholder = "Confirm Password" id="cpassword" name = "cpassword" maxlength="40" class = "span-7" />
                            </div>
                            <div class = "span-7 last add-margin-above-10">
                                <input type = "submit" class = "yellow-button" value = "Signup" />
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>