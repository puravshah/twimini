<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>




             <link rel="stylesheet" href="/static/css/blueprint/screen.css"  type="text/css" media="screen,projection"/>
             <link rel="stylesheet" href="/static/css/blueprint/print.css"  type="text/css" media="print"/>

             <!-- [if lt IE 8]>
             <link rel="stylesheet" href="/static/css/blueprint/ie.css"  type="text/css" media="screen,projection"/>
             <![endif]-->

             <link rel="stylesheet" href="/static/css/blueprint/style.css" type="text/css"/>
             <link rel="stylesheet" href="/static/css/blueprint/design.css"  type="text/css" />
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

        <script type = "text/javascript" src = "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type = "text/javascript" src = "/static/js/ejs_production.js"></script>
        <script type = "text/javascript" src = "/static/js/external_js_file.js">

        </script>
    </head>

    <body >

        <div class= "container ">

          <div class ="corner-round-all" id="header">
                        <div style="float:right;">
            				<form action="/search/" method="get" style="display:inline;">
            				<input type="text" name="search" style="padding:0px;padding:4px;-webkit-border-radius:3px;-moz-border-radius:3px;border-radius:3px;" id = "searchBox"/>
            				<input <input type = "button" value = "Search" id = "searchButton" onclick = "search()"  style="padding:0px;padding:4px;-webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px;" />
            				</form>
                            <span style="font-size:1em;color:#fafafc;">
                              <a style="font-family:Arial;color:white" href= "/logout">logout</a>
                            </span>
                         </div>



             <ul class="navlist" style="font-size:1.2em;">
				<li><img href=""></li>
				<li><a href="/tweet">Home</a></li>
				<li><a href="/user">Profile</a></li>

			 </ul>

             <ul>
                <li>
                  <span style="font-size:1.5em;font-family:Arial;color:white;float:left; padding-left:10px; padding-right:40px;"><a style="color:#000000" href = "/user"><%= session.getAttribute("firstname") %></a>
                  </span>
                </li>
             </ul>


                        <div style="float:left;">
                        HI
                        </div>



          </div>

      <!-- notify bar -->
	  <div id="notebar" class="corner-round-bottom">
	    <div class="corner-round-bottom-inner">
		 <div id="notebar-content">Welcome to Want it
		 </div>
	    </div>
	  </div>

	<!-- main page area -->
	<div id="constrain" class="corner-round-all">
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

        <div  id = "leftpart">
            <div>
                <input type = "text" name = "tweet" id = "tweetBox" />
                <input type = "button" value = "Add" onclick= "createTweet({name:'<%= session.getAttribute("name") %>'})" />
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
                    You have no followers.
                </div>

                <script type = "text/javascript">
                    $('#followingDiv').hide();
                    $('#followerDiv').hide();
                </script>

            </div>
        </div>
      </div>
    </div>
    </body>
</html>
