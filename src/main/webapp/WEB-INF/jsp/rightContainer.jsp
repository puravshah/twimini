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
                            <a href="javascript:getFollowing( {uid:${uid}, user:<%= request.getParameter("user") %>} )">
                                Following<br/>
                                <span id="following-count">${followingCount}</span>
                            </a>
                        </div>

                        <div class="span-2 last center-text">
                            <a href="javascript:getFollowers( {uid:${uid}, user:<%= request.getParameter("user") %>} )">
                                Followers<br/>
                                <span id="follower-count">${followerCount}</span>
                            </a>
                        </div>
                    </div>

                    <div id="following-thumbs" class="span-8 last add-margin-above-20">
                        <div class="span-8 last">
                            <h5>Following. <a href="javascript:getFollowing( {uid:${uid}, user:<%= request.getParameter("user") %>} )">view all</a></h5>
                        </div>

                        <div class="span-8 last add-margin-above-10">
                            <ul id="following-thumbs-container">
                                <c:forEach var='item' items='${followingList}'>
                                    <script type="text/javascript">
                                        var html = new EJS({url: '/static/ejs/thumbs.ejs'}).render({uid: ${item.uid}, name: '${item.name}'});
                                        //var thumbLi = $(html);
                                        //$('#following-thumbs-container').append(thumbLi);
                                        dojo.place(html, "following-thumbs-container", "last");
                                    </script>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <div id="follower-thumbs" class="span-8 last add-margin-above-20">
                        <div class="span-8 last">
                            <h5>Followers. <a href="javascript:getFollowers( {uid:${uid}, user:<%= request.getParameter("user") %>} )">view all</a></h5>
                        </div>

                        <div class="span-8 last add-margin-above-10">
                            <ul id="follower-thumbs-container">
                                <c:forEach var='item' items='${followerList}'>
                                    <script type="text/javascript">
                                        var html = new EJS({url: '/static/ejs/thumbs.ejs'}).render({uid: ${item.uid}, name: '${item.name}'});
                                        //var thumbLi = $(html);
                                        //$('#follower-thumbs-container').append(thumbLi);
                                        dojo.place(html, "follower-thumbs-container", "last");
                                    </script>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>