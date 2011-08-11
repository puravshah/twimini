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
                        <form id="searchForm" action="/search" method = "post">
                            <input type="text" name="query" id="search-box" onkeyup = "showResults(this)" onkeydown = "showResults(this)" autocomplete="off"/>
                            <input type="hidden" name="start" value="0" id = "currentSearchCount"/>
                            <input type="hidden" name="count" value="10" id = "currentSearchCountValue"/>
                            <input type="submit" value="Search" id="search-button"/>
                        </form>
                    </div>

                    <div class = "span-7" id = "search-dropdown">
                        <ul id = "search-results"></ul>

                        <div id = 'loadMoreSearchResults' class = "center-text">
                            See More Results
                        </div>
                    </div>

                    <c:if test="${not empty sessionScope.name}">
                        <div id="dropdown-text" class="span-3 append-2 center-text last" onclick="toggleDropdown()">
                            <a href="javascript:void(0);">${sessionScope.name}</a>
                            <img src="/static/images/icon_dropdown_1.png"/>
                        </div>

                        <div id="dropdown" class="span-3">
                            <div class="span-3 last">
                                <a href="/user/edit">Edit Profile</a>
                            </div>
                            <div class="span-3 last">
                                <a href="/logout">Logout</a>
                            </div>
                        </div>

                        <script type="text/javascript">
                            var parentInfo = dojo.position('dropdown-text', true);
                            var hiddenChild = dojo.byId('dropdown');
                            var hiddenChildInfo = dojo.position('dropdown', true);
                            var left = parentInfo.x + parentInfo.w - hiddenChildInfo.w - 9; //-9 is by trial and error
                            var bottom = parentInfo.y + parentInfo.h;
                            //alert(parentInfo.x + ", " + left + ", " + parentInfo.w + ", " + hiddenChildInfo.w);

                            dojo.style(hiddenChild, {
                                left: left + "px",
                                top: bottom + "px"
                            });
                            dojo.style("dropdown", "display", "none");
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

            <script type = "text/javascript">
                var dropdown = dojo.byId('search-dropdown');
                var searchBoxPos = dojo.position(dojo.byId('search-box'), true);
                var dropdownPos = dojo.position(dropdown, true);

                dojo.style(dropdown, {
                    left: searchBoxPos.x - (dropdownPos.w - searchBoxPos.w) / 2 - 7,
                    top: searchBoxPos.y + searchBoxPos.h
                });
                //alert( "(" + searchBoxPos.x + " " + searchBoxPos.w + "), (" + dropdownPos.x + " " + dropdownPos.w + ")");
                dojo.style(dropdown, 'display', 'none');
            </script>
