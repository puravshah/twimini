<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <style type="text/css">
            @import "/static/dojoroot/dijit/themes/tundra/tundra.css";
            @import "/static/dojoroot/dojo/resources/dojo.css" ;
        </style>
        <%@include file="head include.txt"%>
        <script type="text/javascript" src="/static/js/external_js_file.js"></script>

        <script>
            dojo.require("dojo.parser" );
            dojo.require("dijit.form.ValidationTextBox");
            history.forward();
        </script>
    </head>

    <body class="tundra">
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
                        <form id = "signup-form" action = "/signup" method = "POST" class="formContainer" onsubmit="return checkEmpty();">
                            <div class = "span-7 last">
                                <input type = "text" placeholder = "Full Name" id= "name" name = "name" class = "span-7"/ >
                            </div>
                            <div class = "span-7 last">
                                <input type = "email" placeholder = "Email" id="email" name = "email" class = "span-7" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "password" placeholder = "Password" id="password" name = "password" class = "span-7" />
                            </div>
                            <div class = "span-7 last">
                                <input type = "password" placeholder = "Confirm Password" id="cpassword" name = "cpassword" class = "span-7" />
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