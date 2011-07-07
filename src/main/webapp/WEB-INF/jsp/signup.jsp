<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <h1> Signup Page</h1>
        <h3>${msg}</h3>
        <div>
            <form action = "/signup" method = "POST">
                <table>
                    <tr>
                        <td>Full name</td>
                        <td><input type = "text" name = "name" /></td>
                    </tr>
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
                        <td><input type = "submit" value = "Signup" /></td>
                        <td><a href = "/login">Cancel</a></td>
                    </tr>
                </table>
                <div>
                    <p>
                        All fields are compulsory
                    </p>
                </div>
            </form>

        </div>
    </body>
</html>
