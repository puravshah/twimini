<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

                    <div id="tab-container" class="span-14 last">
                        <div class="span-2 tab tab-active" onclick="${param.firstTabUrl}">
                            <span>${param.firstTabName}</span>
                        </div>
                        <div class="span-2 tab"
                             onclick="getFollowing( {uid:${uid}, user:${sessionScope.uid}} );">
                            <span>Following</span>
                        </div>
                        <div class="span-2 tab last"
                             onclick="getFollowers( {uid:${uid}, user:${sessionScope.uid}} );">
                            <span>Followers</span>
                        </div>
                    </div>

                    <div class="span-14 last">
                        <div id="tweetDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfTweets'>
                                <script type="text/javascript">
                                    ${param.firstTabUrl}
                                </script>
                            </div>
                        </div>

                        <div id="followingDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfFollowing'>

                            </div>
                        </div>

                        <div id="followerDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfFollower'>

                            </div>
                        </div>

                        <script type="text/javascript">
                            $('#followingDiv').hide();
                            $('#followerDiv').hide();
                        </script>
                    </div>