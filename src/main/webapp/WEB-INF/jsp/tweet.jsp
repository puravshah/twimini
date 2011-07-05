<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <script type = "text/javascript" src = "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type = "text/javascript" src = "/static/js/ejs_production.js"></script>

        <script type = "text/javascript">
            function createTweet() {
                firstname = '<%= session.getAttribute("firstname") %>';
                $.ajax({
                    url: "/tweet/create.json",
                    type: "POST",
                    data: "tweet=" + document.getElementById("tweetBox").value,
                    success: function(data) {
                        data.firstname = firstname;
                        prependTweet(data);
                    }
                });
            }

            function prependTweet(data) {
                var html = new EJS({url: '/static/tweetItem.ejs'}).render(data);
                var tweetItemLi = $(html);
                $('#ListOfTweets').prepend(tweetItemLi);
            }

            function prependFollowing(data) {

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

            function search() {
                searchText = document.getElementById("searchBox").value;
                alert(searchText);
            }

        </script>
    </head>

    <body>
        <div id = "navbar">
            <a href = "/tweet">Home</a>
            <a href = "/user">Profile</a>
            <input type = "text" name = "search" id = "searchBox" />
            <input type = "button" value = "Search" id = "searchButton" onclick = "search()" />
            <a href = "/user"><%= session.getAttribute("firstname") %></a>
            <a href= "/logout">logout</a>
            <br /> <br /> <br />
        </div>

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
                            prependTweet({pid:${item.pid}, uid:${item.uid}, firstname:'<%= session.getAttribute("firstname") %>', tweet:'${item.tweet}', timestamp:'${item.timestamp}'});
                        </script>
                    </c:forEach>
                </ul>
            </div>

            <div id = "followingDiv">
                <ul id = 'ListOfFollowing'>
                    <c:forEach var = 'item' items = '${followingList}'>
                        <script type="text/javascript">
                            prependFollowing({pid:${item.pid}, uid:${item.uid}, firstname:'<%= session.getAttribute("firstname") %>', tweet:'${item.tweet}', timestamp:'${item.timestamp}'});
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
    </body>
</html>
