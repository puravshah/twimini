<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <script type = "text/javascript" src = "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type = "text/javascript" src = "/static/js/ejs_production.js"></script>
        <script type = "text/javascript">
            function createTweet() {
                name = '<%= session.getAttribute("name") %>';
                $.ajax({
                    url: "/tweet/create.json",
                    type: "POST",
                    data: "tweet=" + document.getElementById("tweetBox").value,
                    success: function(data) {
                        data.name = name;
                        prependTweet(data);
                    }
                });
            }

            function prependTweet(data) {
                var html = new EJS({url: '/static/tweetItem.ejs'}).render(data);
                var tweetItemLi = $(html);
                $('#ListOfTweets').prepend(tweetItemLi);
            }

            function appendFollowing(data) {
                var html = new EJS({url: 'static/followingItem.ejs'}).render(data);
                var followingItemLi = $(html);
                $('#ListOfFollowing').append(followingItemLi);
            }

            function getFeed() {
                $('#tweetDiv2').show();
                $('#followingDiv2').hide();
                $('#followerDiv2').hide();
            }

            function getTweets() {
                $('#tweetDiv').show();
                $('#followingDiv').hide();
                $('#followerDiv').hide();
            }

            function getFollowing() {
                $('#tweetDiv').hide();
                $('#followingDiv').show();
                $('#followerDiv').hide();
            }

            function getFollowers() {
                $.ajax({
                    url: "/user/follower.json",
                    type: "POST",
                    data: "uid=" + <%= session.getAttribute("uid") %>,
                    success: function(data) {
                        $('#tweetDiv').hide();
                        $('#followingDiv').hide();
                        $('#followerDiv').show();
                    }
                });
            }

            function unfollow(id) {
                $.ajax({
                    url: "/user/unfollow.json",
                    type: "POST",
                    data: "unfollowId=" + id,
                    success: function(data) {
                        $('#followingItem_' + id).remove();
                    }
                });
            }

            function search() {
                searchText = document.getElementById("searchBox").value;
                alert(searchText);
            }

        </script>
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

        <div id = "leftpart">
            <div>
                <input type = "text" name = "tweet" id = "tweetBox" />
                <input type = "button" value = "Add" onclick= "createTweet()" />
            </div>

            <div>
                <br /> <br />
                <div>
                    <a href = "javascript:getTweets()">Tweet</a>
                    <a href = "javascript:getFollowing()">Following</a>
                    <a href = "javascript:getFollowers()">Followers</a>
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
                    You have no followers.
                </div>

                <script type = "text/javascript">
                    $('#followingDiv').hide();
                    $('#followerDiv').hide();
                </script>

            </div>
        </div>
    </body>
</html>
