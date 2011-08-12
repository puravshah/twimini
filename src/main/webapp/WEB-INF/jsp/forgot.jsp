<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <%@include file="head include.txt" %>
    </head>

    <body>
        <div class="container">
            <jsp:include page="indexHeader.jsp">
                <jsp:param name="url" value="/"></jsp:param>
            </jsp:include>

            <div class="prepend-5 span-15 append-4 last add-margin-above-40">
                <div class="span-15 last rounded-box">
                    <div class="span-15 last center-text">
                        <h3>Forgot Password</h3>
                        <hr />
                    </div>

                    <div id="msg-container" class="span-15 last center-text">
                        <h4>Enter your Email id here, and we'll send you a reset link to change your password</h4>
                    </div>

                    <div id="error-box" class="error-box prepend-2 span-11 append-2 last add-margin-above-20">
                        <div id="error-content" class="error-content prepend-1 span-9 append-1 last"></div>
                    </div>

                    <script type = "text/javascript">
                        dojo.style("error-box", "display", "none");
                    </script>

                    <div class=" prepend-3 span-8 append-4 add-margin-above-20 last">
                        <div class="span-6">
                            <input id="email" type="email" name="email" placeholder="Email" maxlength="40" size="30"/>
                        </div>
                        <div class="span-2 last">
                            <input type="button" value="Submit" onclick="doForgot();"/>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
