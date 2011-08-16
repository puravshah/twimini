<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <%@include file="head include.txt"%>
    </head>

    <body>
        <div class="container">
            <jsp:include page="navigationHeader.jsp"></jsp:include>

            <div id="left-right-container" class="add-margin-above-20 span-24">
                <div id="left-container" class="span-14">
                    <div class="span-14 last">
                        <div class="span-14 last">
                            <h3>Post a tweet</h3>
                        </div>

                        <div class="span-12">
                            <textarea name="tweet" id="tweet-box" height='20' width='70' onkeyup="javascript:checkCharacterLimit(this)" onkeydown="javascript:checkCharacterLimit(this)"></textarea>
                        </div>

                        <div class="span-2 last add-margin-above-20">
                            <input id = "tweet-button" value="Tweet" onclick="createTweet({name:'${name}', user: '${sessionScope.uid}'})" type="button" disabled = "disabled"/>
                        </div>

                        <div id="char-count" class="span-2 last">
                            <span id="tweet-char-left">140</span>
                            <span>left</span>
                        </div>
                    </div>

                    <jsp:include page="leftContainerBody.jsp">
                        <jsp:param name="firstTabName" value="Feed"></jsp:param>
                        <jsp:param name="firstTabUrl" value="getFeed({uid:${uid}, user:'${sessionScope.uid}'}, false)"></jsp:param>
                        <jsp:param name="loadMoreTweetsUrl" value="getFeed({uid:${uid}, user:'${sessionScope.uid}'}, true)"></jsp:param>
                    </jsp:include>
                </div>

              <jsp:include page="rightContainer.jsp">
                  <jsp:param name="tweetUrl" value="/user?uid=${uid}"></jsp:param>
              </jsp:include>

            </div>

        </div>
    </body>
</html>
