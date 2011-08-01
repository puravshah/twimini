<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <%@include file="head include.txt"%>
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