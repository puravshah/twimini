            <div class = "span-24 last header">
                <div id = "twitter-logo" class = "span-13">
                    <a href = "<%= request.getParameter("url") %>"><img src = "/static/images/logo.png" alt = "Mini Twitter" /></a>
                </div>
                <div id = "login-handler" class = "span-10 last">
                    <form action = "/login" method = "POST" onsubmit="return checkLoginEmpty();">
                        <div class = "span-4">
                            <input type = "email" placeholder = "Email" id="name" name = "email" class = "span-4" maxlength="40"/>
                        </div>
                        <div class = "span-4">
                            <input type = "password" placeholder = "Password" id="password" name = "password" class = "span-4" maxlength="40"/>
                        </div>
                        <div class = "span-2 last add-padding-above-5">
                            <input type = "submit" value = "Login" />
                        </div>
                    </form>
                    <div class = "span-4">
                        <a href = "/forgot">Forgot your password?</a>
                    </div>
                </div>
            </div>