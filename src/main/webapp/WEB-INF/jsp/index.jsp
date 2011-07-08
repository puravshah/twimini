<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <h1> Mini Twitter </h1>
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
            <p>${loginMsg}</p>
        </div>

        <div>
            <h4>
                Not a user? Sign up here
            </h4>
            <div>
                <form action = "/signup" method = "POST">
                    <table>
                        <tr>
                            <td>Email id</td>
                            <td><input type = "text" name = "email" /></td>
                        </tr>
                        <tr>
                            <td>Password</td>
                            <td><input type = "password" name = "password" /></td>
                        </tr>
                        <tr>
                            <td>Confirm Password</td>
                            <td><input type = "password" name = "cpassword" /></td>
                        </tr>
                        <tr>
                            <td>Full name</td>
                            <td><input type = "text" name = "name" /></td>
                        </tr>
                    </table>
                    <td><input type = "submit" value = "Signup" /></td>
                </form>
                ${signupMsg}
            </div>
        </div>
    </body>
</html>