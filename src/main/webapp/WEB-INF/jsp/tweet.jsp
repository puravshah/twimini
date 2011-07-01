<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <h3>
            Welcome ${firstname},
            <a href= "/logout">logout</a>
        </h3>

        <div>
            <form action = "/tweet" method = "post">
                <input type = "text" name = "tweetbox" />
                <input type = "submit" value = "Add" />
            </form>
        </div>

        <div>
            <h1>Tweet Feed</h1>
        </div>
    </body>
</html>
