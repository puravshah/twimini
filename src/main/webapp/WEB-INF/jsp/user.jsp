<%@ page import="javax.net.ssl.SSLEngineResult" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/static/css/blueprint/screen.css"/>
        <link rel="stylesheet" type="text/css" href="/static/css/blueprint/print.css"/>
        <link rel="stylesheet" type="text/css" href="/static/css/blueprint/style.css"/>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type="text/javascript" src="/static/js/ejs_production.js"></script>
        <script type="text/javascript" src="/static/js/external_js_file.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dojo/dojo.xd.js" type="text/javascript"></script>
    </head>

    <body>
        <div class="container">
            <div id="navigation-bar" class="span-24 last header">
                <div id="twitter-logo" class="span-6">
                    <img src="/static/images/logo.png" alt="Mini Twitter"/>
                </div>

                <div id="user-nav-head" class span="18 last">
                    <div class="span-2">
                        <a href="/tweet">Home</a>
                    </div>

                    <div class="span-2">
                        <a href="/user?uid=<%= session.getAttribute("uid") %>">Profile</a>
                    </div>

                    <div class="span-9 search-box">
                        <input type="text" name="q" id="search-box"/>
                        <input type="button" value="Search" id="search-button" onclick="search()"/>
                    </div>

                    <div id="dropdown-text" class="span-2" onclick="toggleDropdown()">
                        <a href="#"><%= session.getAttribute("name") %>
                        </a>
                        <img src="/static/images/icon_dropdown_1.png"/>
                    </div>
                </div>

                <div id="dropdown" class="span-2">
                    <div class="span-2 last">
                        <a href="/user/edit?uid=${uid}">Edit Profile</a>
                    </div>
                    <div class="span-2 last add-margin-above-20">
                        <a href="/logout">Logout</a>
                    </div>
                </div>
                <script type="text/javascript">
                    $('#dropdown').hide();
                </script>
            </div>

            <div id="left-right-container" class="add-margin-above-20 span-24">
                <div id="left-container" class="span-14">
                    <div id="profile-info" class="span-14 last">
                        <div id="photo-container" class="span-4">
                            <a href=""><img height="100" width="125" src="/static/images/default-profile-picture.gif"/></a>
                        </div>

                        <div id="info" class="span-10 last">
                            <h2>${name} </h2>
                            <span>${email}</span>
                        </div>
                    </div>

                    <div id="user-edit-follow" class="span-13 last">
                        <% if (session.getAttribute("uid").equals(request.getAttribute("uid"))) { %>
                            <a href="/user/edit?uid=${uid}">Edit Profile</a>
                        <% }
                        else {
                            if ((Integer) request.getAttribute("status") == 0) { %>
                                <input type="button" value="follow" onclick="userAction(this, ${uid})" />
                            <% }
                            else { %>
                                <input type="button" value="unfollow" onclick="userAction(this, ${uid})" />
                            <% } %>
                        <% } %>
                    </div>

                    <div id="tab-container" class="span-14 last">
                        <div class="span-2 tab tab-active" onclick="getTweets();">
                            <span>Tweet</span>
                        </div>
                        <div class="span-2 tab"
                             onclick="getFollowing( {uid:${uid}, user:<%= session.getAttribute("uid") %>} );">
                            <span>Following</span>
                        </div>
                        <div class="span-2 tab last"
                             onclick="getFollowers( {uid:${uid}, user:<%= session.getAttribute("uid") %>} );">
                            <span>Followers</span>
                        </div>
                    </div>

                    <div class="span-14 last">
                        <div id="tweetDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfTweets'>
                                <c:forEach var='item' items='${tweetList}'>
                                    <script type="text/javascript">
                                        var tweet = filter('${item.tweet}');
                                        prependTweet({pid:${item.pid}, uid:${item.uid}, name:'${name}', tweet:tweet, timestamp:'${item.timestamp}'});
                                    </script>
                                </c:forEach>
                            </div>
                        </div>

                        <div id="followingDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfFollowing'>
                                <c:forEach var='item' items='${followingList}'>
                                    <script type="text/javascript">
                                        appendFollowing({id:${item.uid}, name:'${item.name}', email:'${item.email}', user:<%= session.getAttribute("uid") %>, status:${item.status}});
                                    </script>
                                </c:forEach>
                            </div>
                        </div>

                        <div id="followerDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfFollower'>
                                <c:forEach var='item' items='${followerList}'>
                                    <script type="text/javascript">
                                        appendFollower({id:${item.uid}, name:'${item.name}', email:'${item.email}', user:<%= session.getAttribute("uid") %>, status:${item.status}});
                                    </script>
                                </c:forEach>
                            </div>
                        </div>

                        <script type="text/javascript">
                            $('#followingDiv').hide();
                            $('#followerDiv').hide();
                        </script>

                    </div>
                </div>

                <div id="right-container" class="span-8 last">
                    <div class="span-8 last">
                        <h2>
                            About
                            <% out.println(session.getAttribute("uid").equals(request.getAttribute("uid")) ? "You" : request.getAttribute("name").toString()); %>
                        </h2>
                    </div>

                    <div class="span-8 last add-margin-above-20">
                        <div class="span-2 colborder center-text">
                            <a href="javascript:getTweets()">
                                Tweets<br/>
                                <span id="tweet-count">${tweetCount}</span>
                            </a>
                        </div>

                        <div class="span-2 colborder center-text">
                            <a href="javascript:getFollowing( {uid:${uid}, user:<%= session.getAttribute("uid") %>} )">
                                Following<br/>
                                <span id="following-count">${followingCount}</span>
                            </a>
                        </div>

                        <div class="span-2 last center-text">
                            <a href="javascript:getFollowers( {uid:${uid}, user:<%= session.getAttribute("uid") %>} )">
                                Followers<br/>
                                <span id="follower-count">${followerCount}</span>
                            </a>
                        </div>
                    </div>

                    <div id="following-thumbs" class="span-8 last add-margin-above-20">
                        <div class="span-8 last">
                            <h5>Following. <a href="javascript:getFollowing( {uid:${uid}, user:<%= session.getAttribute("uid") %>} )">view all</a></h5>
                        </div>

                        <div class="span-8 last add-margin-above-10">
                            <ul id="following-thumbs-container">
                                <c:forEach var='item' items='${followingList}'>
                                    <script type="text/javascript">
                                        var html = new EJS({url: '/static/ejs/thumbs.ejs'}).render({uid: ${item.uid}, name: '${item.name}'});
                                        var thumbLi = $(html);
                                        $('#following-thumbs-container').append(thumbLi);
                                    </script>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <div id="follower-thumbs" class="span-8 last add-margin-above-20">
                        <div class="span-8 last">
                            <h5>Followers. <a href="javascript:getFollowers( {uid:${uid}, user:<%= session.getAttribute("uid") %>} )">view all</a></h5>
                        </div>

                        <div class="span-8 last add-margin-above-10">
                            <ul id="follower-thumbs-container">
                                <c:forEach var='item' items='${followerList}'>
                                    <script type="text/javascript">
                                        var html = new EJS({url: '/static/ejs/thumbs.ejs'}).render({uid: ${item.uid}, name: '${item.name}'});
                                        var thumbLi = $(html);
                                        $('#follower-thumbs-container').append(thumbLi);
                                    </script>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
