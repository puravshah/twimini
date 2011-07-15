<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/screen.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/print.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/style.css" />
        <script type = "text/javascript" src = "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type = "text/javascript" src = "/static/js/ejs_production.js"></script>
        <script type = "text/javascript" src = "/static/js/external_js_file.js"></script>
    </head>

    <body>
        <div class = "container">
            <div id = "navigationBar" class = "span-24 last header">
                <div id = "twitter-logo" class = "span-6">
                    <img src = "/static/images/logo.png" alt = "Mini Twitter" />
                </div>

                <div id = "user-nav-head" class span = "18 last">
                    <div class = "span-2">
                        <a href = "/tweet">Home</a>
                    </div>

                    <div class = "span-2">
                        <a href = "/user?uid=<%= session.getAttribute("uid") %>">Profile</a>
                    </div>

                    <div class = "span-9 search-box">
                        <input type = "text" name = "search" id = "searchBox" />
                        <input type = "button" value = "Search" id = "searchButton" onclick = "search()" />
                    </div>

                    <div id = "dropdownText" class = "span-2" onclick = "toggleDropdown()">
                        <span><%= session.getAttribute("name") %></span>
                        <img src = "/static/images/icon_dropdown_1.png" />
                    </div>
                </div>
                <div id = "dropdown">
                    <ul>
                        <li><a href = "/user/edit?uid=${uid}">Edit Profile</a></li>
                        <li><a href = "/logout">Logout</a></li>
                    </ul>
                </div>
                <script type = "text/javascript">
                    var p = $("#dropdownText");
                    var offset = p.offset();
                    $('#dropdown').offset({ top: offset.bottom, right: offset.right});
                    $('#dropdown').hide();
                </script>
            </div>

            <div class = "add-padding-above-20 span-24">
                <div id = "leftContainer" class = "span-15">
                    <div id = "profileInfo" class = "span-15 last">
                        <div id = "photoContainer" class = "span-3">
                            <a href = ""><img height = "50" width = "50" src = "/static/images/default-profile-picture.gif" /></a>
                        </div>

                        <div id = "info" class = "span-12 last">
                            ${name} <br />
                            ${email}
                        </div>

                        <div class = "span-15 last add-padding-above-5">
                            <% if( session.getAttribute("uid").equals(request.getAttribute("uid")) ) { %>
                                <a href = "/user/edit?uid=${uid}">Edit Profile</a>
                            <% } else { %>
                                <a href = "/user/follow">Follow</a>
                            <% } %>
                        </div>
                    </div>

                    <div class = "span-15 last add-padding-above-20">
                        <div class = "span-4">
                            <a href = "javascript:getTweets()">Tweet</a>
                        </div>
                        <div class = span-4>
                            <a href = "javascript:getFollowing()">Following</a>
                        </div>
                        <div class = "span-4 last">
                            <a href = "javascript:getFollowers({uid:${uid}})">Followers</a>
                        </div>
                    </div>

                    <div class = "span-15 last">
                        <div id = "tweetDiv" class = "span-15 last add-padding-above-20">
                            <div id = 'ListOfTweets'>
                                <c:forEach var = 'item' items = '${tweetList}'>
                                    <script type="text/javascript">
                                        prependTweet({pid:${item.pid}, uid:${item.uid}, name:'${name}', tweet:'${item.tweet}', timestamp:'${item.timestamp}'});
                                    </script>
                                </c:forEach>
                            </div>
                        </div>

                        <div id = "followingDiv" class = "span-15 last add-padding-above-20">
                            <ul id = 'ListOfFollowing'>
                                <c:forEach var = 'item' items = '${followingList}'>
                                    <script type="text/javascript">
                                        appendFollowing({uid:${item.uid}, name:'${item.name}', email:'${item.email}'});
                                    </script>
                                </c:forEach>
                            </ul>
                        </div>

                        <div id = "followerDiv" class = "span-15 last add-padding-above-20">

                        </div>

                        <script type = "text/javascript">
                            $('#followingDiv').hide();
                            $('#followerDiv').hide();
                        </script>

                    </div>
                </div>

                <div id = "rightContainer" class = "span-9 last">
                    <div id = "tweetCount">
                        <p>
                            You have posted
                            <a href = "javascript: getTweets()">${tweetCount} Tweets</a>
                        </p>
                    </div>

                    <div id = "followingCount">
                        <p>
                            You are Following
                            <a href = "javascript: getFollowing()">${followingCount} People</a>
                            <div id = "followingThumbnails">

                            </div>
                        </p>
                    </div>

                    <div id = "followerCount">
                        <p>
                            You have
                            <a href = "javascript:getFollowers({uid:<%= session.getAttribute("uid") %>})">
                                ${followerCount} Followers
                            </a>
                        </p>
                        <div id = "followerThumbnails">

                        </div>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
