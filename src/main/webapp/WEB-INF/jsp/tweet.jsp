<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>





             <link rel="stylesheet" href="/static/css/blueprint/style.css" type="text/css"/>
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


         function resize(){
            //document.body.style.height=document.body.clientHeight;
            document.getElementById("header").style.width=document.body.clientWidth;

         }

        </script>


         <script type = "text/javascript" src = "/static/js/external_js_file.js">

        </script>
    </head>

    <body style="background:url(/static/images/bobwood01.jpg)   scroll 0 0 transparent;padding-left:150px">

        <div   onload="resize();">

          <div id="header" class ="corner-round-all" style=" max-width:1024px;"  >
                        <div style="float:right;">
            				<form action="/search/" method="get" class ="SearchForm" style="display:inline;">

                			 <input type="text"  size="20";name="search" class="searchField  corner-round-all "; id = "searchBox"/>

            				 <input type = "button" value = "Search" class = " searchButton" id = "searchButton" onclick = "search()"/>

            				</form>
                            <span style="font-size:1em;color:#fafafc;">
                            <a style="font-family:Arial;color:#fff" href= "/logout">logout</a>
                            </span>
                          <br>
                         </div>



             <ul class="navlist" style="font-size:1.2em;">

				<li class="navlist4"><img src="/static/images/1282.jpg" width="50px" heigth="10px"></li>
				<li class="navlist3"><a href="/tweet">Home</a></li>
				<li class="navlist3"><a href="/user">Profile</a></li>

			 </ul>

           </div>

        <div id="notebar" class="corner-round-bottom">
          welcome
        </div>


	<!-- main page area -->
    <div id="page-outer">
	  <div id="page-container" class="corner-round-all">

        <div>
            <div class="main-content" style="min-width:282px">
               <div>
                  <input type = "text" name = "tweet" id = "tweetBox" />
                  <input type = "button" value = "Add" onclick= "createTweet({name:'<%= session.getAttribute("name") %>'})" />
               </div>

               <div>
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
            <div>
            <div class="dashboard">
              <div class="component">
                 followers
              </div>
            </div>
        </div>
    <div>
   </div>
    </body>
</html>
