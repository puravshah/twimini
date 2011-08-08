function setInnerText(element, text) {
    if (document.all) element.innerText = text;
    else element.textContent = text;
}

function getInnerText(element) {
    if (document.all) return element.innerText;
    return element.textContent;
}

function checkEmpty(){
     var email=dojo.byId("email").value;
     var password=dojo.byId("password").value;
     if(email==null)
     {
        window.location="/login";
        return false;
     }
     else if(email==null)
     {
        window.location="/login";
        return false;
     }
}

function checkEmpty(){
    var name=dojo.byId("name").value;
    alert(name);
    var email=dojo.byId("email").value ;
    var password=dojo.byId("password").value;
    var cpassword=dojo.byId("cpassword").value;

    if(name==null)
    {
        window.location="/signup";
        return false;
    }
    else if(email==null)
    {
        window.location="/signup";
        return false;
    }
    else if(password==null && cpassword==null)
    {
        if(password==cpassword)
        {
           window.location="/signup";
           return false;
        }
    }

}
function filter(str) {
    str = str.replace(/[&]/g, '&amp;');
    str = str.replace(/[ ]/g, '&nbsp;');
    str = str.replace(/[<]/g, '&lt;');
    str = str.replace(/[>]/g, '&gt;');
    str = str.replace(/[']/g, '&#39;');
    str = str.replace(/["]/g, '&quot;');
    str = str.replace(/[\n]/g, '<br />');
    return str;
}

function checkCharacterLimit(field) {
    dojo.byId("tweet-button").disabled = (field.value.length <= 0);
    if (field.value.length > 140) {
        field.value = field.value.substring(0, 140);
    }
    setInnerText(dojo.byId("tweet-char-left"), 140 - field.value.length);
}

function createTweet(input) {
    var str = dojo.byId("tweet-box").value;

    dojo.xhrPost({
        url: "/tweet/create",
        handleAs: "json",
        content: {'tweet': str},
        load: function(data) {
            if (data.status === "0") alert("Unable to add tweet: " + data.errorMessage);
            else {
                data = data.tweetDetails;
                data.name = input.name;
                prependTweet(data);
                dojo.byId("tweet-box").value = "";
                dojo.byId("currentTweetCount").value = (parseInt(dojo.byId("currentTweetCount").value) + 1);
                //alert(dojo.byId("currentTweetCount").value);
            }
        },
        error: function(error) {
            //alert(error);
            alert('You need to login first');
        }
    });
}

function getAccount() {
    $("#accountDiv").show();
    $("#passwordDiv").hide();
    $("#imageDiv").hide();
}

function getPassword() {
    $("#accountDiv").hide();
    $("#passwordDiv").show();
    $("#imageDiv").hide();
}

function getImage() {
    $("#accountDiv").hide();
    $("#passwordDiv").hide();
    $("#imageDiv").show();
}

function makeTabActiveOnEdit(index) {
    var ids = ['accountDiv', 'passwordDiv', 'imageDiv'];
    var div = dojo.byId("tab-container").getElementsByTagName("div");
    for(var i = 0; i < 3; i++) {
        dojo.removeClass(div[i], "tab-active");
        dojo.style(ids[i], "display", i == index ? "block" : "none");
    }
    dojo.addClass(div[index], "tab-active");
}

function makeTabActive(index) {
    var ids = ['tweetDiv', 'followingDiv', 'followerDiv'];
    var div = dojo.byId("tab-container").getElementsByTagName("div");
    for(var i = 0; i < 3; i++) {
        dojo.removeClass(div[i], "tab-active");
        dojo.style(ids[i], "display", i == index ? "block" : "none");
    }
    dojo.addClass(div[index], "tab-active");
}

function prependTweet(data) {
    var html = new EJS({url: '/static/ejs/tweetItem.ejs'}).render(data);
    dojo.place(html, "ListOfTweets", "first");
}

function appendTweet(data) {
    var html = new EJS({url: '/static/ejs/tweetItem.ejs'}).render(data);
    dojo.place(html, "ListOfTweets", "last");
}

function appendFollowing(data) {
    data.divId = 'followingItem_' + data.id;
    var html = new EJS({url: 'static/ejs/followItem.ejs'}).render(data);
    dojo.place(html, "ListOfFollowing", "last");
}

function appendFollower(data) {
    data.divId = 'followerItem_' + data.id;
    var html = new EJS({url: 'static/ejs/followItem.ejs'}).render(data);
    dojo.place(html, "ListOfFollower", "last")
}

function getFeed(input) {
    var start = 0;//loadMore ? dojo.byId("currentTweetCount").value : 0;
    dojo.xhrPost({
        url: "/tweet/getFeed",
        handleAs: "json",
        content: {uid:input.uid, start:start},
        load: function(data) {
            if (data.status == 0) {
                alert(data.errorMessage);
                return;
            }

            data = data.feed;
            makeTabActive(0);

            dojo.empty('ListOfTweets');
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                var tweet = filter(item.tweet.tweet);
                appendTweet({pid:item.tweet.pid, uid:item.tweet.uid, name: item.name, tweet:tweet, timestamp:item.tweet.timestamp});
            }

            dojo.style("loadMoreTweets", "display", (data.length < 10) ? "none" : "block");
            dojo.byId('currentTweetCount').value = data.length;
            //alert("start : " + dojo.byId('currentTweetCount').value);
        },
        error: function(error) {
            //alert('error : ' + error);
            alert('You need to login first');
        }
    });
}

function getTweets(input) {
    dojo.xhrPost({
        url: "/tweet/getTweetList",
        handleAs: "json",
        content: {uid:input.uid, start:0},
        load: function(data) {
            if (data.status == 0) {
                alert(data.errorMessage);
                return;
            }

            data = data.tweets;
            makeTabActive(0);

            dojo.empty('ListOfTweets');
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                var tweet = filter(item.tweet);
                appendTweet({pid:item.pid, uid:item.uid, name:input.name, tweet:tweet, timestamp:item.timestamp});
            }

            dojo.style("loadMoreTweets", "display", (data.length < 10) ? "none" : "block");
            dojo.byId('currentTweetCount').value = data.length;
            //alert("start : " + dojo.byId('currentTweetCount').value);
        },
        error: function(error) {
            alert('error : ' + error);
        }
    });
}


function loadMoreFeed(input) {
    var start = dojo.byId("currentTweetCount").value;
    dojo.xhrPost({
        url: "/tweet/getFeed",
        handleAs: "json",
        content: {uid:input.uid, start:start},
        load: function(data) {
            if (data.status == 0) {
                alert(data.errorMessage);
                return;
            }

            data = data.feed;
            makeTabActive(0);

            for (var i = 0; i < data.length; i++) {
                item = data[i];
                var tweet = filter(item.tweet.tweet);
                appendTweet({pid:item.tweet.pid, uid:item.tweet.uid, name: item.name, tweet:tweet, timestamp:item.tweet.timestamp});
            }

            dojo.style("loadMoreTweets", "display", (data.length < 10) ? "none" : "block");
            dojo.byId('currentTweetCount').value = (data.length + parseInt(dojo.byId('currentTweetCount').value));
            //alert("start : " + dojo.byId('currentTweetCount').value);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function loadMoreTweets(input) {
    var start = dojo.byId("currentTweetCount").value;
    dojo.xhrPost({
        url: "/tweet/getTweetList",
        handleAs: "json",
        content: {uid:input.uid, start:start},
        load: function(data) {
            if (data.status == 0) {
                alert(data.errorMessage);
                return;
            }

            data = data.tweets;
            makeTabActive(0);

            for (var i = 0; i < data.length; i++) {
                item = data[i];
                var tweet = filter(item.tweet);
                appendTweet({pid:item.pid, uid:item.uid, name:input.name, tweet:tweet, timestamp:item.timestamp});
            }

            dojo.style("loadMoreTweets", "display", (data.length < 10) ? "none" : "block");
            dojo.byId('currentTweetCount').value = (data.length + parseInt(dojo.byId('currentTweetCount').value));
            //alert("start : " + dojo.byId('currentTweetCount').value);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function getFollowing(input) {
    dojo.xhrPost({
        url: "/user/getFollowing",
        handleAs: "json",
        content: {uid:input.uid},
        load: function(data) {
            if (data.status == 0) {
                alert(data.errorMessage);
                return;
            }

            data = data.following
            makeTabActive(1);
            dojo.empty('ListOfFollowing');

            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollowing({id:item.uid, name:item.name, email:item.email, user:input.user, status:item.status});
            }
            setInnerText(dojo.byId("following-count"), data.length);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function getFollowers(input) {
    dojo.xhrPost({
        url: "/user/getFollowers",
        handleAs: "json",
        content: {uid:input.uid},
        load: function(data) {
            if (data.status == 0) {
                alert(data.errorMessage);
                return;
            }

            data = data.followers;
            makeTabActive(2);
            dojo.empty('ListOfFollower');
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollower({id:item.uid, name:item.name, email:item.email, user:input.user, status:item.status});
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
        url: "user/unfollow",
        handleAs: "json",
        content: {id:id},
        load: function(data) {
            if (data.status === "1") {
                setInnerText(button, "Follow");
                dojo.removeClass(button, "follow-unfollow-button");
                dojo.addClass(button, "follow-button");
            }
            else alert(data.errorMessage);
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
            }
            else alert(data.errorMessage);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function doForgot() {
    var email = dojo.byId("email").value;
    if (email === "") {
        dojo.byId("forgot-msg").innerHTML = "<span>Please enter your email id</span>";
        return;
    }

    dojo.xhrPost({
        url: "/forgot",
        handleAs: "json",
        content: {email: email},
        load: function(data) {
            if (data == true) {
                dojo.byId("msg-container").innerHTML = "<span><h5>An email has been sent to you, which contains the instructions to reset your password</h5></span>";
                dojo.byId("forgot-msg").innerHTML = "<span></span>";
            }
            else {
                dojo.byId("forgot-msg").innerHTML = "<span>Invalid email. We do not have a record of your email id</span>";
            }
        },
        error: function(error) {
            alert(error);
        }
    });
}

function doReset() {
    var password = dojo.byId("password").value;
    var cpassword = dojo.byId("cpassword").value;
    var uid = dojo.byId("uid").value;

    if (password === "" || cpassword === "") {
        dojo.byId("forgot-msg").innerHTML = "<span>Please fill out all the fields</span>";
        return false;
    }
    if (password !== cpassword) {
        dojo.byId("forgot-msg").innerHTML = "<span>The passwords don't match</span>";
        return false;
    }

    dojo.xhrPost({
        url: "/reset",
        handleAs: "json",
        content: {password: password, cpassword: cpassword, uid: uid},
        load: function(data) {
            if (data == true) {
                dojo.byId("msg-container").innerHTML = "<span><h5>Password successfully changed.</h5></span>";
                dojo.byId("forgot-msg").innerHTML = "<span></span>";
            }
            else {
                dojo.byId("forgot-msg").innerHTML = "<span>Unable to change your password</span>";
            }

            dojo.byId("password").value = "";
            dojo.byId("cpassword").value = "";
        },
        error: function(error) {
            alert(error);
        }
    });
    return false;
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

function showResetFields() {
    $('#reset-fields-container').show();
    $('#reset-show-text').hide();
}

function changeButtonText(button) {
    if (getInnerText(button) === 'Following') setInnerText(button, "Unfollow");
}

function restoreOriginal(button) {
    if (getInnerText(button) === 'Unfollow') setInnerText(button, "Following");
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