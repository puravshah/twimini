function setInnerText(element, text) {
    if(document.all) element.innerText = text;
    else element.textContent = text;
}

function getInnerText(element) {
    if(document.all) return element.innerText;
    return element.textContent;
}

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
    if (field.value.length > 140) {
        field.value = field.value.substring(0, 140);
    }
    setInnerText(dojo.byId("tweet-char-left"), 140 - field.value.length);
}

function createTweet(datas) {
    name = datas.name;
    var str = dojo.byId("tweet-box").value;

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
                dojo.byId("tweet-box").value = "";
                setInnerText(dojo.byId("tweet-char-left"), 140);
                setInnerText(dojo.byId("tweet-count"), parseInt(getInnerText(dojo.byId("tweet-count"))) + 1);
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

            var divs = dojo.byId("tab-container").getElementsByTagName("div");
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

    var divs = dojo.byId("tab-container").getElementsByTagName("div");
    var newClass = "span-2 tab tab-active";
    divs[0].setAttribute("class", newClass);
    divs[1].setAttribute("class", "span-2 tab");
    divs[2].setAttribute("class", "span-2 tab last");
}

function getTweets() {
    $('#tweetDiv').show();
    $('#followingDiv').hide();
    $('#followerDiv').hide();

    var divs = dojo.byId("tab-container").getElementsByTagName("div");
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

            var divs = dojo.byId("tab-container").getElementsByTagName("div");
            var newClass = "span-2 tab tab-active";
            divs[0].setAttribute("class", "span-2 tab");
            divs[1].setAttribute("class", newClass);
            divs[2].setAttribute("class", "span-2 tab last");

            $('#ListOfFollowing').empty();
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollowing({id:item.uid, name:item.name, email:item.email, user:datas.user, status:item.status});
            }
            setInnerText(dojo.byId("following-count"), data.length);
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

            var divs = dojo.byId("tab-container").getElementsByTagName("div");
            var newClass = "span-2 tab last tab-active";
            divs[0].setAttribute("class", "span-2 tab");
            divs[1].setAttribute("class", "span-2 tab");
            divs[2].setAttribute("class", newClass);

            $('#ListOfFollower').empty();
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollower({id:item.uid, name:item.name, email:item.email, user:datas.user, status:item.status});
            }
            setInnerText(dojo.byId("follower-count"), data.length);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function userAction(button, id) {
    if (getInnerText(button) === 'Follow') follow(button, id);
    else unfollow(button, id);
}

function unfollow(button, id) {
    dojo.xhrPost({
        url: "/user/unfollow",
        handleAs: "json",
        content: {id:id},
        load: function(data) {
            if (data.status === "1") {
                setInnerText(button, "Follow");
                dojo.removeClass(button, "follow-unfollow-button");
                dojo.addClass(button, "follow-button");
                button.removeAttribute("onmouseover");
                button.removeAttribute("onmouseout");
            }
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
            if (data.status === "1") {
                setInnerText(button, "Following");
                dojo.removeClass(button, "follow-button");
                dojo.addClass(button, "follow-unfollow-button");
                button.setAttribute("onmouseover", "changeButtonText(" + button + ");");
                button.setAttribute("onmouseout", "restoreOriginal(" + button + ");");
            }
            else alert(data.errorMsg);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function search() {
    searchText = dojo.byId("search-box").value;
    alert(searchText);
}

function toggleDropdown() {
    $("#dropdown").toggle();
}

function toggleLoginDropdown() {
    $("#login-dropdown").toggle();
}

function changeButtonText(button) {
    setInnerText(button, "Unfollow");
}

function restoreOriginal(button) {
    setInnerText(button, "Following");
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