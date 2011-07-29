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
        <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dojo/dojo.xd.js" type="text/javascript"></script>
    </head>

    <body>
        <div class = "container">
            <jsp:include page = "indexHeader.jsp"></jsp:include>

            <div id = "body-container" class = "container">
                <div class = "prepend-5 span-15 append-4 last">
                    <div id = "reset-container" class = "span-15">
                        <div id = "msg-container" class = "span-15 last">
                            <span>
                                <h5>Forgot your password? Just enter your Email id here, and we'll send you a reset code. Use that reset code to change your password.</h5>
                            </span>
                        </div>

                        <div class = "span-5 append-10 add-margin-above-20 last">
                            <input id = "email" type = "email" name = "email" placeholder = "Email" />
                            <input type = "button" value = "Submit" onclick = "doForgot();" />
                        </div>

                        <div id = "forgot-msg" class = "span-15 forgot-msg last add-margin-above-20">
                            ${msg}
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
