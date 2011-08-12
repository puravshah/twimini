<%@page contentType="text/html;charSet=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <%@include file="head include.txt" %>

    </head>
    <body>
        <div class="container">
            <jsp:include page="navigationHeader.jsp"></jsp:include>

            <div id="left-right-container" class="add-margin-above-20 span-24">
                <div id="left-container" class="span-14">
                    <div class="span-14 last">
                        <div class="span-14 last center-text">
                            <h2>Your search results</h2>
                        </div>
                    </div>

                    <div class="span-14 last">
                        <div id="followingDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfFollowing'>
                                <script type="text/javascript">
                                    search( {query: '${query}', user:'${sessionScope.uid}', start: ${start}, count: ${count}}, false );
                                </script>
                            </div>
                            <div id = "loadMoreSearch" class = "span-14 last add-margin-above-20">
                                <input type = "button" value = "Load More" onclick = "search({query: '${query}', user:'${sessionScope.uid}'}, true)"/>
                            </div>
                        </div>
                    </div>

                    <div id="error-box" class="error-box prepend-2 span-10 append-2 last add-margin-above-20">
                        <div id="error-content" class="error-content prepend-1 span-9 append-1 last">
                            There are no results to display
                        </div>
                    </div>

                    <script type = "text/javascript">
                        dojo.style("error-box", "display", "none");
                    </script>
                </div>

                <jsp:include page="rightContainer.jsp">
                    <jsp:param name="tweetUrl" value="/user?uid=${uid}"></jsp:param>
                </jsp:include>
            </div>
        </div>
    </body>
</html>