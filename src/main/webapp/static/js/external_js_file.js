function filter(str) {
    str = str.replace(/[&]/g, '&amp;');
    str = str.replace(/[<]/g, '&lt;');
    str = str.replace(/[>]/g, '&gt;');
    str = str.replace(/[']/g, '&#39;');
    str = str.replace(/["]/g, '&quot;');
    str = str.replace(/[\n]/g, '<br>');
    str = str.replace(/[ ]/g, '&nbsp;');
    return str;
}

function checkCharacterLimit(field) {
    if(field.value.length > 140) {
        field.value = field.value.substring(0, 140);
    }
    document.getElementById("tweet-char-left").innerText = 140 - field.value.length;
}

function createTweet(datas) {
    name = datas.name;
    var str = document.getElementById("tweet-box").value;

    dojo.xhrPost({
        url: "/tweet/create",
        handleAs: "json",
        content: {'tweet': str},
        load: function(data) {
            if (data.status === "0") alert("Unable to add tweet: " + data.errorMsg);
            else {
                data.name = name;
                data.tweet = filter(data.tweet);
                prependTweet(data);
                document.getElementById("tweet-box").value = "";
                document.getElementById("tweet-char-left").innerText = 140;
                document.getElementById("tweet-count").innerText = parseInt(document.getElementById("tweet-count").innerText) + 1;
            }
        },
        error: function(error) {
            alert(error);
        }
    });
}

function prependTweet(data) {
    var html = new EJS({url: '/static/ejs/tweetItem.ejs'}).render(data);
    var tweetItemLi = $(html);
    $('#ListOfTweets').prepend(tweetItemLi);
}

function appendFollowing(data) {
    data.divId = 'followingItem_' + data.id;
    var html = new EJS({url: 'static/ejs/followItem.ejs'}).render(data);
    var followingItemLi = $(html);
    $('#ListOfFollowing').append(followingItemLi);
}

function appendFollower(data) {
    data.divId = 'followerItem_' + data.id;
    var html = new EJS({url: 'static/ejs/followItem.ejs'}).render(data);
    var followerItemLi = $(html);
    $('#ListOfFollower').append(followerItemLi);
}

function getFeed(datas) {
    dojo.xhrPost({
        url: "/tweet/getFeed",
        handleAs: "json",
        content: {uid:datas.uid},
        load: function(data) {
            $('#tweetDiv').show();
            $('#followingDiv').hide();
            $('#followerDiv').hide();

            var divs = document.getElementById("tab-container").getElementsByTagName("div");
            var newClass = "span-2 tab tab-active";
            divs[0].setAttribute("class", newClass);
            divs[1].setAttribute("class", "span-2 tab");
            divs[2].setAttribute("class", "span-2 tab last");

            $('#ListOfTweets').empty();
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                prependTweet({pid:item.pid, uid:item.uid, name: item.name, tweet:item.tweet, timestamp:item.timestamp});
            }
        },
        error: function(error) {
            alert(error);
        }
    });

    $('#tweetDiv').show();
    $('#followingDiv').hide();
    $('#followerDiv').hide();

    var divs = document.getElementById("tab-container").getElementsByTagName("div");
    var newClass = "span-2 tab tab-active";
    divs[0].setAttribute("class", newClass);
    divs[1].setAttribute("class", "span-2 tab");
    divs[2].setAttribute("class", "span-2 tab last");
}

function getTweets() {
    $('#tweetDiv').show();
    $('#followingDiv').hide();
    $('#followerDiv').hide();

    var divs = document.getElementById("tab-container").getElementsByTagName("div");
    var newClass = "span-2 tab tab-active";
    divs[0].setAttribute("class", newClass);
    divs[1].setAttribute("class", "span-2 tab");
    divs[2].setAttribute("class", "span-2 tab last");
}

function getFollowing(datas) {
    dojo.xhrPost({
        url: "/user/getFollowing",
        handleAs: "json",
        content: {uid:datas.uid},
        load: function(data) {
            $('#tweetDiv').hide();
            $('#followingDiv').show();
            $('#followerDiv').hide();

            var divs = document.getElementById("tab-container").getElementsByTagName("div");
            var newClass = "span-2 tab tab-active";
            divs[0].setAttribute("class", "span-2 tab");
            divs[1].setAttribute("class", newClass);
            divs[2].setAttribute("class", "span-2 tab last");

            $('#ListOfFollowing').empty();
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollowing({id:item.uid, name:item.name, email:item.email, user:datas.user, status:item.status});
            }
            document.getElementById("following-count").innerText = data.length;
        },
        error: function(error) {
            alert(error);
        }
    });
}

function getFollowers(datas) {
    dojo.xhrPost({
        url: "/user/getFollower",
        handleAs: "json",
        content: {uid:datas.uid},
        load: function(data) {
            $('#tweetDiv').hide();
            $('#followingDiv').hide();
            $('#followerDiv').show();

            var divs = document.getElementById("tab-container").getElementsByTagName("div");
            var newClass = "span-2 tab last tab-active";
            divs[0].setAttribute("class", "span-2 tab");
            divs[1].setAttribute("class", "span-2 tab");
            divs[2].setAttribute("class", newClass);

            $('#ListOfFollower').empty();
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollower({id:item.uid, name:item.name, email:item.email, user:datas.user, status:item.status});
            }
            document.getElementById("follower-count").innerText = data.length;
        },
        error: function(error) {
            alert(error);
        }
    });
}

function userAction(button, id) {
    if (button.value === 'follow') follow(button, id);
    else unfollow(button, id);
}

function unfollow(button, id) {
    dojo.xhrPost({
        url: "/user/unfollow",
        handleAs: "json",
        content: {id:id},
        load: function(data) {
            if (data.status === "1") button.value = "follow";
            else alert(data.errorMsg);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function follow(button, id) {
    dojo.xhrPost({
        url: "/user/follow",
        handleAs: "json",
        content: {id:id},
        load: function(data) {
            if (data.status === "1") button.value = "unfollow";
            else alert(data.errorMsg);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function search() {
    searchText = document.getElementById("search-box").value;
    alert(searchText);
}

function toggleDropdown() {
    $("#dropdown").toggle();
}

function toggleLoginDropdown() {
    $("#login-dropdown").toggle();
}

function validate(data) {
    var valid = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890_-.";
    for (var i = data.length() - 1; i >= 0; i--) {
        var ok = 0;
        for (var j = valid.length() - 1; j >= 0; j--)
            if (valid[j] == data[i]) {
                ok = 1;
                break;
            }
        if (ok == 0) return false;
    }
    return true;
}