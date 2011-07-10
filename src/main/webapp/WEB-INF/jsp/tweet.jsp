<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/screen.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/print.css" />
        <script type = "text/javascript" src = "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type = "text/javascript" src = "/static/js/ejs_production.js"></script>
        <script type = "text/javascript" src = "/static/js/external_js_file.js"></script>
    </head>

    <body>
        <div id = "navbar">
            <a href = "/tweet">Home</a>
            <a href = "/user?uid=<%= session.getAttribute("uid") %>">Profile</a>
            <input type = "text" name = "search" id = "searchBox" />
            <input type = "button" value = "Search" id = "searchButton" onclick = "search()" />
            <a href = "/user?uid=<%= session.getAttribute("uid") %>"><%= session.getAttribute("name") %></a>
            <a href= "/logout">logout</a>
            <br /> <br /> <br />
        </div>

        <div id = "leftContainer">
            <div>
                <input type = "text" placeholder = "Share a new Tweet" name = "tweet" id = "tweetBox" />
                <input type = "button" value = "Share" onclick= "createTweet({name:'<%= session.getAttribute("name") %>'})" />
            </div>

            <div>
                <br /> <br />
                <div>
                    <a href = "javascript:getTweets()">Tweet</a>
                    <a href = "javascript:getFollowing()">Following</a>
                    <a href = "javascript:getFollowers(<%= session.getAttribute("uid") %>)">Followers</a>
                </div>

                <div id = "tweetDiv">
                    <ul id = 'ListOfTweets'>
                        <c:forEach var = 'item' items = '${tweetList}'>
                            <script type="text/javascript">
                                prependTweet({pid:${item.pid}, uid:${item.uid}, name:'${item.name}', tweet:'${item.tweet}', timestamp:'${item.timestamp}'});
                            </script>
                        </c:forEach>
                    </ul>
                </div>

                <div id = "followingDiv">
                    <ul id = 'ListOfFollowing'>
                        <c:forEach var = 'item' items = '${followingList}'>
                            <script type="text/javascript">
                                appendFollowing({uid:${item.uid}, name:'${item.name}', email:'${item.email}'});
                            </script>
                        </c:forEach>
                    </ul>
                </div>

                <div id = "followerDiv">

                </div>

                <script type = "text/javascript">
                    $('#followingDiv').hide();
                    $('#followerDiv').hide();
                </script>

            </div>
        </div>

        <div id = "rightContainer">
            <div id = "tweetCount">
                <p>
                    You have posted
                    <a href = "/user?uid=<%= session.getAttribute("uid") %>">${tweetCount} Tweets</a>
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
    </body>
</html>
