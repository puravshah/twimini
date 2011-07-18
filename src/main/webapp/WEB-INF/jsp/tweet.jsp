
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
        <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dojo/dojo.xd.js" type="text/javascript"></script>
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
                        <input type = "text" name = "q" id = "search-box" />
                        <input type = "button" value = "Search" id = "search-button" onclick = "search()" />
                    </div>

                    <div id = "dropdown-text" class = "span-2" onclick = "toggleDropdown()">
                        <a href = "#"><%= session.getAttribute("name") %></a>
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
                    <div class = "span-14 last">
                        <div class = "span-14 last">
                            <h3>Post a tweet</h3>
                        </div>

                        <div class = "span-12">
                            <textarea name="tweet" id = "tweetBox" height = '20' width = '70'></textarea>
                        </div>

                        <div class="span-2 last add-margin-above-20">
                            <input value = "Tweet" onclick = "createTweet({name:${uid}})" type="button" />
                        </div>
                    </div>

                    <div id = "tab-container" class = "span-14 last">
                        <div class = "span-2 tab tab-active" onclick = "getFeed();">
                            <span>Feed</span>
                        </div>
                        <div class = "span-2 tab" onclick = "getFollowing();">
                            <span>Following</span>
                        </div>
                        <div class = "span-2 tab last" onclick = "getFollowers();">
                            <span>Followers</span>
                        </div>
                    </div>

                    <div class = "span-14 last">
                        <div id = "tweetDiv" class = "span-14 last add-padding-above-20">
                            <div id = 'ListOfTweets'>
                                <c:forEach var = 'item' items = '${tweetList}'>
                                    <script type="text/javascript">
                                        prependTweet({pid:${item.pid}, uid:${item.uid}, name: '${item.name}', tweet:'${item.tweet}', timestamp:'${item.timestamp}'});
                                    </script>
                                </c:forEach>
                            </div>
                        </div>

                        <div id = "followingDiv" class = "span-14 last add-padding-above-20">
                            <div id = 'ListOfFollowing'>
                                <c:forEach var = 'item' items = '${followingList}'>
                                    <script type="text/javascript">
                                        appendFollowing({uid:${item.uid}, name:'${item.name}', email:'${item.email}'});
                                    </script>
                                </c:forEach>
                            </div>
                        </div>

                        <div id = "followerDiv" class = "span-14 last add-padding-above-20">
                            <div id = 'ListOfFollower'>
                                <c:forEach var = 'item' items = '${followerList}'>
                                    <script type="text/javascript">
                                        appendFollower({uid:${item.uid}, name:'${item.name}', email:'${item.email}'});
                                    </script>
                                </c:forEach>
                            </div>
                        </div>

                        <script type = "text/javascript">
                            $('#followingDiv').hide();
                            $('#followerDiv').hide();
                        </script>

                    </div>
                </div>

                <div id = "right-container" class = "span-8 last">
                    <div class = "span-8 last">
                        <h2>About You</h2>
                    </div>

                    <div class = "span-8 last add-margin-above-20">
                        <div class = "span-2 colborder">
                            <a href = "/user?uid=${uid}">Tweets<br />${tweetCount}</a>
                        </div>

                        <div class = "span-2 colborder">
                            <a href = "javascript:getFollowing()">Following<br />${followingCount}</a>
                        </div>

                        <div class = "span-2 last">
                            <a href = "javascript:getFollowers()">Followers<br />${followerCount}</a>
                        </div>
                    </div>

                    <div id = "follower-thumbs" class = "span-8 last">
                        <div class = "span-8 last">
                            <h5>Following. <a href = "javascript:getFollowing()">view all</a></h5>
                        </div>

                        <div class = "span-8 last">
                            <ul id = "following-thumbs-container">
                                <c:forEach var = 'item' items = '${followerList}'>
                                    <script type = "text/javascript">
                                        var html = new EJS({url: '/static/js/thumbs.ejs'}).render( {uid: ${item.uid}} );
                                        var thumbLi = $(html);
                                        $('#following-thumbs-container').append(thumbLi);
                                    </script>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                     <div id = "following-thumbs" class = "span-8 last">
                        <div class = "span-8 last">
                            <h5>Followers. <a href = "javascript:getFollowers()">view all</a></h5>
                        </div>

                        <div class = "span-8 last">
                            <ul id = "follower-thumbs-container">
                                <script type = "text/javascript">

                                </script>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
