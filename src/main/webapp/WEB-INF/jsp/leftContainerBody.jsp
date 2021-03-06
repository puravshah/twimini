<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

                    <div id="tab-container" class="span-14 last">
                        <div class="span-2 tab tab-active" onclick="${param.firstTabUrl}">
                            <span>${param.firstTabName}</span>
                        </div>
                        <div class="span-2 tab"
                             onclick="getFollowing( {uid:'${uid}', user:'${sessionScope.uid}'}, false );">
                            <span>Following</span>
                        </div>
                        <div class="span-2 tab last"
                             onclick="getFollowers( {uid:${uid}, user:'${sessionScope.uid}'}, false );">
                            <span>Followers</span>
                        </div>
                        <div class="span-2 tab last"
                             onclick="getFavourites( {uid:${uid}, user:'${sessionScope.uid}'}, false );">
                            <span>Favourites</span>
                        </div>
                    </div>

                    <input type = "hidden" id = "currentTweetCount" value = "0" />
                    <input type = "hidden" id = "currentFollowingCount" value = "0" />
                    <input type = "hidden" id = "currentFollowersCount" value = "0" />
                    <input type = "hidden" id = "currentFavouriteCount" value = "0" />
                    <div class="span-14 last">
                        <div id="tweetDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfTweets'>
                                <script type="text/javascript">
                                    ${param.firstTabUrl}
                                </script>
                            </div>
                            <div class = "add-margin-above-20 span-14 last" id = "loadMoreTweets">
                                <input type = "button" value = "Load More" onclick = "${param.loadMoreTweetsUrl}">
                            </div>
                        </div>

                        <div id="followingDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfFollowing'>

                            </div>
                            <div class = "add-margin-above-20 span-14 last" id = "loadMoreFollowing">
                                <input type = "button" value = "Load More" onclick = "getFollowing( {uid:'${uid}', user:'${sessionScope.uid}'}, true )">
                            </div>
                        </div>

                        <div id="followerDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfFollower'>

                            </div>
                            <div class = "add-margin-above-20 span-14 last" id = "loadMoreFollowers">
                                <input type = "button" value = "Load More" onclick = "getFollowers( {uid:'${uid}', user:'${sessionScope.uid}'}, true )">
                            </div>
                        </div>
                        <div id="favouriteDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfFavourites'>

                            </div>
                            <div class = "add-margin-above-20 span-14 last" id = "loadMoreFavourites">
                                <input type = "button" value = "Load More" onclick = "getFavourites( {uid:'${uid}', user:'${sessionScope.uid}'}, true )">
                            </div>
                        </div>



                        <script type="text/javascript">
                            dojo.style('followingDiv', "display", "none");
                            dojo.style('followerDiv', "display", "none");
                            dojo.style('favouriteDiv','display','none');
                        </script>
                    </div>