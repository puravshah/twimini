<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

                <div id="right-container" class="span-8 last">
                    <div class="span-8 last">
                        <h2>
                            About
                            <%--<script type = "text/javascript">
                                var sessionId = '${sessionScope.uid}';
                                var requestId = '${uid}';
                                var name = '${name}';
                                document.write( (sessionId == requestId) ? "You" : name );
                            </script>--%>

                            <c:choose>
                                <c:when test="${not empty sessionScope.uid && sessionscope.uid == requestscope.uid}">
                                    <c:out value="You"></c:out>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${requestScope.name}"></c:out>
                                </c:otherwise>
                            </c:choose>
                        </h2>
                    </div>

                    <div class="span-8 last add-margin-above-20">
                        <div class="span-2 colborder center-text">
                            <a href="${param.tweetUrl}">
                                Tweets<br/>
                                <span id="tweet-count">${tweetCount}</span>
                            </a>
                        </div>

                        <div class="span-2 colborder center-text">
                            <a href="javascript:getFollowing( {uid:${uid}, user:'${sessionScope.uid}'}, false )">
                                Following<br/>
                                <span id="following-count">${followingCount}</span>
                            </a>
                        </div>

                        <div class="span-2 last center-text">
                            <a href="javascript:getFollowers( {uid:${uid}, user:'${sessionScope.uid}'}, false )">
                                Followers<br/>
                                <span id="follower-count">${followerCount}</span>
                            </a>
                        </div>
                    </div>

                    <div id="following-thumbs" class="span-8 last">
                        <div class="span-8 last">
                            <h5>Following. <a href="javascript:getFollowing( {uid:${uid}, user:'${sessionScope.uid}'}, false )">view all</a></h5>
                        </div>

                        <div class="span-8 last add-margin-above-10">
                            <ul id="following-thumbs-container">
                                <c:forEach var='item' items='${followingList}'>
                                    <script type="text/javascript">
                                        var html = new EJS({url: '/static/ejs/thumbs.ejs'}).render({uid: ${item.uid}, name: '${item.name}'});
                                        dojo.place(html, "following-thumbs-container", "last");
                                    </script>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <div id="follower-thumbs" class="span-8 last">
                        <div class="span-8 last">
                            <h5>Followers. <a href="javascript:getFollowers( {uid:${uid}, user:'${sessionScope.uid}'}, false )">view all</a></h5>
                        </div>

                        <div class="span-8 last add-margin-above-10">
                            <ul id="follower-thumbs-container">
                                <c:forEach var='item' items='${followerList}'>
                                    <script type="text/javascript">
                                        var html = new EJS({url: '/static/ejs/thumbs.ejs'}).render({uid: ${item.uid}, name: '${item.name}'});
                                        dojo.place(html, "follower-thumbs-container", "last");
                                    </script>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <c:if test = "${not empty sessionScope.uid}">
                        <div class="span-8 last add-margin-above-40">
                            <h3>Invite Friends</h3>
                            <div class="span-4 append-4 last">
                                <input id="email" type="email" placeholder="Email Address" onfocus="clearError()">
                            </div>
                            <div class="span-3 append-5 last">
                                <input style="width: 100px" type="button" name="send"  value="Send" onclick="inviteFriends()" width="30px">
                            </div>
                        </div>
                        <div id="error-msg">

                        </div>
                    </c:if>
                </div>