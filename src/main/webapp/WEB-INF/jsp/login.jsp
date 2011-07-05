<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <h1> Login Page</h1>
        <div>
            <h3> ${msg} </h3>
        </div>

        <div>
            <form action = "/login" method = "POST">
                <table>
                    <tr>
                        <td>Email id</td>
                        <td><input type = "text" name = "email" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type = "password" name = "password" /></td>
                    </tr>
                </table>
                <input type = "submit" value = "Login" />
            </form>
        </div>

        <div>
            <h4>
                Not a user?
                <a href = "/signup">Sign up</a>
            </h4>
        </div>
    </body>
</html>