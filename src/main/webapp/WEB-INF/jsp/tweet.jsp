<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <script type = "text/javascript" src = "http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type = "text/javascript" src = "/static/js/ejs_production.js"></script>

        <script type = "text/javascript">
            function createTweet(form) {
                $.post('/tweet/create.json', $(form).serialize(), function(data) {
                    var html = new EJS({url: '/static/tweetItem.ejs'}).render(data);
                    var tweetItemLi = $(html);
                    $('#ListOfTweets').prepend(tweetItemLi);
                });
            }

            function prependItem(data) {
                var html = new EJS({url: '/static/tweetItem.ejs'}).render(data);
                var tweetItemLi = $(html);
                $('#ListOfTweets').prepend(tweetItemLi);
            }

        </script>
    </head>

    <body>
        <h3>
            Welcome ${firstname},
            <a href= "/logout">logout</a>
        </h3>

        <div>
            <form action = "/tweet/create.json" method = "post" onsubmit = "createTweet(this); return false;">
                <input type = "text" name = "tweet" />
                <input type = "submit" value = "Add" />
            </form>
        </div>

        <div>
            <h1>Tweet Feed</h1>
            <div>
                <ul id = 'ListOfTweets'>
                    <c:forEach var = 'item' items = '${tweetList}'>
                        <!--<li>
                            <a href= '#'>${firstname}</a> <br />
                            ${item.tweet} <br />
                            Posted on: ${item.timestamp}
                        </li>-->
                        <script type="text/javascript">
                            prependItem({pid:${item.pid}, uid:${item.uid}, firstname:'${firstname}', tweet:'${item.tweet}', timestamp:'${item.timestamp}'});
                        </script>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </body>
</html>
