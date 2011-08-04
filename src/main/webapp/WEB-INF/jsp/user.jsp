<%@ page import="javax.net.ssl.SSLEngineResult" %>
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
                    <div id="profile-info" class="span-14 last">
                        <div id="photo-container" class="span-4">
                            <a href=""><img height="100" width="125" src="/image/${uid}.png"/></a>
                        </div>

                        <div id="info" class="span-10 last">
                            <h2>${name} </h2>
                            <span>${email}</span>
                        </div>
                    </div>

                    <div id="user-edit-follow" class="span-13 last">
                       <%--<script>
                            var sessionUid = "${sessionScope.uid}";
                            var requestUid = "${requestScope.uid}";
                            var status = ${requestScope.status};

                            if(!sessionUid) document.write('<span class = "follow-button" onclick = "userAction(this, ${uid});">Follow</span>');
                            else if(sessionUid == requestUid) document.write('<a href="/user/edit">Edit Profile</a>');
                            else {
                                if(status == 1) document.write('<span class = "follow-unfollow-button" onMouseover = "changeButtonText(this)" onmouseout = "restoreOriginal(this)" onclick = "userAction(this, ${uid});">Following</span>');
                                else document.write('<span class = "follow-button" onclick = "userAction(this, ${uid});">Follow</span>');
                            }
                        </script>--%>

                           <c:choose>
                            <c:when test="${not empty sessionScope.uid && sessionScope.uid == requestScope.uid}">
                                <a href="/user/edit">Edit Profile</a>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${requestScope.status == 1}">
                                        <span class = "follow-unfollow-button" onMouseover = "changeButtonText(this)" onmouseout = "restoreOriginal(this)" onclick = "userAction(this, ${uid});">Following</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class = "follow-button" onclick = "userAction(this, ${uid});">Follow</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <jsp:include page="leftContainerBody.jsp">
                        <jsp:param name="firstTabName" value="Tweets"></jsp:param>
                        <jsp:param name="firstTabUrl" value="getTweets( {uid: ${uid}, name: '${name}'} );"></jsp:param>
                    </jsp:include>
                </div>

                <jsp:include page="rightContainer.jsp">
                    <jsp:param name="tweetUrl" value="javascript:getTweets( {uid: ${uid}, name: '${name}'} );"></jsp:param>
                </jsp:include>
            </div>

        </div>
    </body>
</html>
