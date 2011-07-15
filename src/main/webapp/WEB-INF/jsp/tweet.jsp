<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

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
        <body style="background:url(/static/images/social-networking-sites.jpg);background-size:950px;overflow:scroll;">
            <div id="header" class="topHeader">
                <div id="tHMB">

                </div>
            </div>
            <div class="container">
                <div class="span-24 last" style="top:0;position:absolute;">
                    <div class="span-5">
                        <img src="/static/images/logo.png" alt="Mini Twitter">
                    </div>

                    <div class="span-1" style="margin-top:15px;color:white;margin-right:5px;text-align:center;padding:3px;">
                        <a href="/tweet">
                            <span>
                                Home
                            </span>
                        </a>
                    </div>
                    <div class="span-1" style="margin-top:15px;color:white;margin-left:10px;margin-right:10px;text-align:center;padding:3px;">
                        <a href="/user">
                            <span>
                                Profile
                            </span>
                        </a>
                    </div>
                    <div class="span-8">
                        <form action="/search/" method="get" class="span-8" style="margin-top:8px;color:white;margin-left:20px;margin-right:10px">
                            <div class="span-7">
                                <input style="width:100%;padding-top:2px;" type="text" name="search">
                            </div>
                            <div class="span-1 pull-2">
                                <input style="margin-top:8px;background:transparent;height:20px;border:none;" type="button" value="Search" onclick="search()">
                            </div>
                        </form>
                    </div>
                    <div class="span-4" style="margin-top:17px;height:20px;border:none;font-size:15px;margin-right:10px">
                        <span style="color:blue">
                            Welcome a
                         </span>
                    </div>
                    <div>
                    <a class="span-2 last " href="/logout" style="margin-top:17px;height:20px;border:none;font-size:15px;">
                        <span>
                            logout
                        </span>
                    </a>
                    </div>
                </div>
                <div id="titleBar" class="corner-round-all corner-round-bottom " style="Background-color:#2D2D2b;margin-top:px">
                </div>
                <div id="page-outer" style="padding-top:1px;padding-left:15px">
                    <div id="page-container" class="span-24 last" style="background-color:#00FFFF;">
                       <div>
                           <div class="main-content span-14" style="min-width:282px;background-color:#00FFFF;">
                                <div>
                                    <div style="margin-top:10px;margin-left:5px;">
                                    <span style="font-style:arial;font-size:22px;color:white;">
                                       what is happening
                                    </span>
                                </div>
                                   <div class="span-14" style="margin-left:10px;">
                                        <textarea style="width:480px;height:40px;" name="tweet" id="tweetBox"></textarea>
                                   </div>
                                   <div class="span-2 " style="float:right;margin-right:10px;">
                                      <input value="tweet" onclick="createTweet({name:'a'})" type="button">
                                   </div>
                                </div>

                                <div class="span-13 header" style="width:540px;margin-top:60px; background-color:black">
                                    <div class="span-2" style="margin-left:5px;font-size:22px;text-decoration:none;">
                                        <a href="javascript:getTweets()">Tweet</a>
                                    </div>
                                    <div class="span-2" style="font-size:22px;">
                                      <a href="javascript:getFollowing()">Following</a>
                                    </div>
                                    <div class="span-2" style="margin-left:30px;font-size:22px;">
                                        <a href="javascript:getFollowers(${uid})">Followers</a>
                                    </div>
                                </div>
                                    <div id="tweetDiv">
                                        <ul id="ListOfTweets">
                                            <c:forEach var = 'item' items = '${tweetList}'>
                                                <script type="text/javascript">
                                                    prependTweet({pid:${item.pid}, uid:${item.uid}, name:'${name}', tweet:'${item.tweet}', timestamp:'${item.timestamp}'});
                                                </script>
                                            </c:forEach>
                                     </ul>
                                </div>

                                    <div style="display: none; " id="followingDiv">
                                        <ul id="ListOfFollowing">
                                               <c:forEach var = 'item' items = '${followingList}'>
                                                    <script type="text/javascript">
                                                        appendFollowing({uid:${item.uid}, name:'${item.name}', email:'${item.email}'});
                                                    </script>
                                                </c:forEach>
                                        </ul>
                                    </div>

                                    <div style="display: none; " id="followerDiv">
                                                <c:forEach var = 'item' items = '${followerList}'>
                                                    <script type="text/javascript">
                                                        appendFollowing({uid:${item.uid}, name:'${item.name}', email:'${item.email}'});
                                                    </script>
                                                </c:forEach>
                                    </div>
                                    <script type="text/javascript">
                                        $('#followingDiv').hide();
                                        $('#followerDiv').hide();
                                    </script>
                                </div>
                            </div>


                            <div class="dashboard">
                                <div class="component">
                                    <div id="tweetCount">
                                        <p>
                                            You have posted
                                            <a href="/user?uid=${uid}">${tweetCount} Tweets</a>
                                        </p>
                                    </div>
                                 <div id="followingCount">
                                    <p>
                                        You are Following
                                        <a href="javascript: getFollowing()"> ${followingCount} People</a>
                                    </p>
                                <div id="followingThumbnails">
                                </div>
                            </div>

                            <div id="followerCount">
                                <p>
                                    You have
                                    <a href="javascript:getFollowers({uid:${uid})">
                                    ${followerCount} Followers
                                    </a>
                                </p>
                            </div>
                            <div id="followerThumbnails">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
