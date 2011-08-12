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

            <div class="prepend-5 span-15 append-4 last add-margin-aboeve-40">
                <div id="reset-container" class="span-15 last">
                    <div class="span-15 last center-text">
                        <h3>Reset Password</h3>
                        <hr />
                    </div>

                    <div id="msg-container" class="span-15 last center-text">
                        <h4>Change your password here</h4>
                    </div>

                    <div id="error-box" class="error-box prepend-2 span-11 append-2 last add-margin-above-20">
                        <div id="error-content" class="error-content prepend-1 span-9 append-1 last"></div>
                    </div>

                    <script type = "text/javascript">
                        dojo.style("error-box", "display", "none");
                    </script>

                    <div class="prepend-5 span-5 append-5 add-margin-above-20 last">
                        <div class = "span-5 last">
                            <input id="newPassword" type="password" name="password" placeholder="New Password"/>
                        </div>
                        <div class = "span-5 last">
                            <input id="confirmPassword" type="password" name="cpassword" placeholder="Confirm Password"/>
                        </div>
                        <div class = "span-5 last">
                            <input type="button" value="Change Password" onclick="doReset();"/>
                        </div>
                        <input id="token" type="hidden" name="token" value="${token}"/>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
