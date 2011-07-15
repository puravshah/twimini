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
            <div id = "navigation-bar" class = "span-24 last header">
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
                        <input type = "text" name = "search" id = "search-box" />
                        <input type = "button" value = "Search" id = "search-button" onclick = "search()" />
                    </div>

                    <div id = "dropdown-text" class = "span-2" onclick = "toggleDropdown()">
                        <span><%= session.getAttribute("name") %></span>
                        <img src = "/static/images/icon_dropdown_1.png" />
                    </div>
                </div>
                <div id = "dropdown" class = "span-2">
                    <div class = "span-2 last">
                        <a href = "/user/edit?uid=${uid}">Edit Profile</a>
                    </div>
                    <div class = "span-2 last add-margin-above-20">
                        <a href = "/logout">Logout</a>
                    </div>
                </div>
                <script type = "text/javascript">
                    $('#dropdown').hide();
                </script>
            </div>

            <div id = "left-right-container" class = "add-margin-above-20 span-24">
                <div id = "left-container" class = "span-14">
                    <div id = "profile-info" class = "span-14 last">
                        <div id = "photo-container" class = "span-4">
                            <a href = ""><img height = "100" width = "125" src = "/static/images/default-profile-picture.gif" /></a>
                        </div>

                        <div id = "info" class = "span-10 last">
                            <h2>${name} </h2>
                            <span>${email}</span>
                        </div>
                    </div>

                    <div id = "user-edit-follow" class = "span-13 last">
                        <% if( session.getAttribute("uid").equals(request.getAttribute("uid")) ) { %>
                            <a href = "/user/edit?uid=${uid}">Edit Profile</a>
                        <% } else { %>
                            <a href = "/user/follow">Follow</a>
                        <% } %>
                    </div>

                    <div id = "tab-container" class = "span-14 last">
                        <a>
                            <span class = "span-2 tab">Tweet</span>
                        </a>
                        <div class = "span-3 tab">
                            <a href = "javascript:getTweets()">Tweet</a>
                        </div>
                        <div class = "span-3 tab">
                            <a href = "javascript:getFollowing()">Following</a>
                        </div>
                        <div class = "span-3 tab last">
                            <a href = "javascript:getFollowers({uid:${uid}})">Followers</a>
                        </div>
                    </div>

                    <div class = "span-14 last">
                        <div id = "tweetDiv" class = "span-14 last add-padding-above-20">
                            <div id = 'ListOfTweets'>
                                <c:forEach var = 'item' items = '${tweetList}'>
                                    <script type="text/javascript">
                                        prependTweet({pid:${item.pid}, uid:${item.uid}, name:'${name}', tweet:'${item.tweet}', timestamp:'${item.timestamp}'});
                                    </script>
                                </c:forEach>
                            </div>
                        </div>

                        <div id = "followingDiv" class = "span-14 last add-padding-above-20">
                            <ul id = 'ListOfFollowing'>
                                <c:forEach var = 'item' items = '${followingList}'>
                                    <script type="text/javascript">
                                        appendFollowing({uid:${item.uid}, name:'${item.name}', email:'${item.email}'});
                                    </script>
                                </c:forEach>
                            </ul>
                        </div>

                        <div id = "followerDiv" class = "span-14 last add-padding-above-20">

                        </div>

                        <script type = "text/javascript">
                            $('#followingDiv').hide();
                            $('#followerDiv').hide();
                        </script>

                    </div>
                </div>

                <div id = "right-container" class = "span-8 last">


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
