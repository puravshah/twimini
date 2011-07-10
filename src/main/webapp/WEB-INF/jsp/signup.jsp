<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/screen.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/print.css" />
    </head>

    <body>
        <div class = "container">
            <h1> Signup Page</h1>

            <div class = "span-4"></div>
            <div class = "span-15">
                <h3>${msg}</h3>
                <form action = "/signup" method = "POST">
                    <div class = "span-9 last">
                        <input type = "text" placeholder = "Full Name" name = "name" /></td>
                    </div>
                        <div class = "span-9 last">
                            <input type = "text" placeholder = "Email" name = "email" /></td>
                        </div>
                        <div class = "span-9 last">
                            <input type = "password" placeholder = "Password" name = "password" /></td>
                        </div>
                        <div class = "span-9 last">
                            <input type = "password" placeholder = "Confirm Password" name = "cpassword" /></td>
                        </div>
                        <div class = "span-9 last">
                            <input type = "submit" value = "Signup" /></td>
                            <td><a href = "/login">Cancel</a></td>
                        </div>
                    </table>
                    <div>
                        <p>
                            All fields are compulsory
                        </p>
                    </div>
                </form>
            </div>

            <div class = "span-4 last">

            </div>
        </div>
    </body>
</html>
