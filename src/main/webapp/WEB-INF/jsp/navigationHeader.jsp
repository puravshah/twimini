<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <div id="navigation-bar" class="span-24 last header">
                <div id="twitter-logo" class="span-6">
                    <img src="/static/images/logo.png" alt="Mini Twitter"/>
                </div>

                <div id="user-nav-head" class span="18 last">
                    <c:if test="${not empty sessionScope.uid}">
                        <div class="span-2">
                            <a href="/tweet">Home</a>
                        </div>
                        <div class="span-2">
                            <a href="/user?uid=${sessionScope.uid}">Profile</a>
                        </div>
                    </c:if>

                    <div class="span-9 search-box">
                        <input type="text" name="q" id="search-box"/>
                        <input type="button" value="Search" id="search-button" onclick="search()"/>
                    </div>

                    <c:if test="${not empty sessionScope.name}">
                        <div id="dropdown-text" class="span-3 append-2 center-text last" onclick="toggleDropdown()">
                            <a href="javascript:void(0);">${sessionScope.name}</a>
                            <img src="/static/images/icon_dropdown_1.png"/>
                        </div>

                        <div id="dropdown" class="span-2">
                            <div class="span-2 last">
                                <a href="/user/edit">Edit Profile</a>
                            </div>
                            <div class="span-2 last add-margin-above-20">
                                <a href="/logout">Logout</a>
                            </div>
                        </div>

                        <script type="text/javascript">
                            dojo.style('dropdown', 'display', 'none');
                        </script>
                    </c:if>

                    <c:if test = "${empty sessionScope.name}">
                        <div class = "prepend-3 span-2">
                            <a href = "/login">Login</a>
                        </div>
                        <div class = "span-2 append-1 last">
                            <a href = "/signup">Signup</a>
                        </div>
                    </c:if>
                </div>
            </div>