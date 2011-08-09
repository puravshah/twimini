<%@page contentType="text/html;charSet=UTF-8" language="java"%>
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
                            <h2>Your search results</h2>
                        </div>
                    </div>

                    <div class="span-14 last">
                        <div id="followingDiv" class="span-14 last add-padding-above-20">
                            <div id='ListOfFollowing'>
                                   <c:forEach var ="item" items="${searchDetails}">
                                        <script type="text/javascript">
                                             appendFollowing({id:${item.uid}, name:'${item.name}', email:'${item.email}', user:'${uid}', status:${item.status}});
                                        </script>
                                   </c:forEach>
                             </div>
                        </div>

                        <script type="text/javascript">
                            dojo.style('followingDiv', "display", "block");
                        </script>
                    </div>
                </div>

              <jsp:include page="rightContainer.jsp">
                  <jsp:param name="tweetUrl" value="/user?uid=${uid}"></jsp:param>
              </jsp:include>

            </div>
        </div>
    </body>
</html>