<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
       <!-- <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/screen.css" />
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/print.css" />
        -->
        <link rel = "stylesheet" type = "text/css" href = "/static/css/blueprint/style.css" />
        <script type = "text/javascript" src = "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type = "text/javascript" src = "/static/js/ejs_production.js"></script>
        <script type = "text/javascript" src = "/static/js/external_js_file.js"></script>
    </head>
        <body>
         <!--<div id="header" class ="topHeader ">
            <div id="tHMB">
                <div id="tHMF">
                    <ol class="tHMFE">
                        <li class="tHMFTP">
                        <span class="tHMFTVS" style="color:White">
                            Welcome <%= session.getAttribute("name") %>
                            <!--<img  src="/static/images/1282.jpg">-->
                        </span>
                        </li>
                        <li class="tHMFT">
                            <a class="tHMFTV" href="/tweet">
                                <span class="tHMFTVS">
                                    Home
                                </span>
                            </a>
                        </li>
                        <li class="tHMFT">
                            <a class="tHMFTV" href="/user">
                                <span class="tHMFTVS">
                                    Profile
                                </span>
                            </a>
                        </li>
                        <li class="tHMFT">
                            <form id="tHMFTV" action="/search/" method="get">
                                <span class="tHMFTVS">
                                    <input  type="text"  name="search" />
                                </span>
                                <span class="tHMFTVS">
                                    <input  type = "button" value = "Search" onclick = "search()"/>
                                </span>
                            </form>
                        </li>
                    </ol>-->

                </div>

               <!-- <div id="tHMS">
                   <ol class="tHMSE">
                       <li class="tHMST">
                            <a class="tHMSTV" href= "/logout">
                                <span class="tHMSTVS">
                                    logout
                                </span>
                            </a>
                       </li>
                   </ol>
                </div>

            </div>

         </div>-->

        <div class = "span-24 last">
            <div class = "span-3">
                <img src = "" />
            </div>

            <div class = "span-3">
                <a href = "/tweet">Home</a>
            </div>

            <div class = "span-3">
                <a href = '/user?uid=<%= session.getAttribute("uid") %>'>">Home</a>
            </div>
        </div>




	        <!-- main page area -->
        <div class = "container">
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

                                </div>
                                <script type = "text/javascript">
                                    $('#followingDiv').hide();
                                    $('#followerDiv').hide();
                                </script>
                            </div>
                        </div>
                    </div>

                    <div class="dashboard">
                        <div class="component">
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
                                </p>
                                <div id = "followingThumbnails">

                                </div>
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
            </div>
        </div>
    </body>
</html>
