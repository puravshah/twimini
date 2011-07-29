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
            <jsp:include page = "indexHeader.jsp"></jsp:include>

            <div id = "body-container" class = "span-24 last">
                <div id = "reset-container" class = "span-5">
                    <div class = "span-5">
                        <form method = "POST" action = "/forgot">
                            <input type = "email" name = "email" placeholder = "Email" />
                            <input type = "submit" value = "Submit" />
                        </form>
                    </div>

                    <div id = "reset-fields-container" class = "span-5">
                        <form method = "POST" action = "/reset">
                            <input type = "text" name = "token" placeholder = "Reset Code" />
                            <input type = "password" name = "password" placeholder = "New Password" />
                            <input type = "password" name = "cpassword" placeholder = "Confirm Password" />
                            <input type = "submit" value = "Submit" />
                        </form>
                    </div>

                    <script type = "text/javascript">
                        $('#reset-fields-container').hide();
                    </script>

                    <div class = "span-2">
                        <span>
                            Already have the reset code?
                            <a href = "javascript:showResetFields();">Click Here</a>
                        </span>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
