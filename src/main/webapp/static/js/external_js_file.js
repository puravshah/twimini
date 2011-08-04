function setInnerText(element, text) {
    if (document.all) element.innerText = text;
    else element.textContent = text;
}

function getInnerText(element) {
    if (document.all) return element.innerText;
    return element.textContent;
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

function getTweet(input) {
    var output;
    dojo.xhrPost({
        url: "/api/tweet/" + input.pid + "/getTweetDetails",
        handleAs: "json",
        load: function(data) {
            data = data.tweetDetails;
            data.name = input.name;
            data.tweet = filter(data.tweet);
            prependTweet(data);

            dojo.byId("tweet-box").value = "";
            setInnerText(dojo.byId("tweet-char-left"), 140);
            setInnerText(dojo.byId("tweet-count"), parseInt(getInnerText(dojo.byId("tweet-count"))) + 1);
        },
        error: function(error) {
            alert(error);
        }
    });
    return output;
}

function createTweet(input) {
    var str = dojo.byId("tweet-box").value;

    dojo.xhrPost({
        url: "/api/tweet/create",
        handleAs: "json",
        content: {'tweet': str},
        load: function(data) {
            if (data.status === "0") alert("Unable to add tweet: " + data.errorMessage);
            else {
                getTweet( {pid: data.pid, name: input.name});
            }
        },
        error: function(error) {
            alert(error);
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


function prependTweet(data) {
    var html = new EJS({url: '/static/ejs/tweetItem.ejs'}).render(data);
    dojo.place(html, "ListOfTweets", "first");
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
    dojo.xhrPost({
        url: "/api/user/" + input.uid + "/getFeed",
        handleAs: "json",
        //content: {uid:input.uid},
        load: function(data) {
            if (data.status == 0) {
                alert(data.errorMessage);
                return;
            }

            data = data.feed;
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
                var tweet = filter(item.tweet.tweet);
                prependTweet({pid:item.tweet.pid, uid:item.tweet.uid, name: item.name, tweet:tweet, timestamp:item.tweet.timestamp});
            }
        },
        error: function(error) {
            alert(error);
        }
    });
}

function getTweets(input) {
    dojo.xhrPost({
        url: "/api/user/" + input.uid + "/getTweetList",
        handleAs: "json",
        //content: {uid:input.uid},
        load: function(data) {
            if (data.status == 0) {
                alert(data.errorMessage);
                return;
            }

            data = data.tweets;
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
                var tweet = filter(item.tweet);
                prependTweet({pid:item.pid, uid:item.uid, name:input.name, tweet:tweet, timestamp:item.timestamp});
            }
        },
        error: function(error) {
            alert(error);
        }
    });
}

function getFollowing(input) {
    dojo.xhrPost({
        url: "/api/user/" + input.uid + "/getFollowing",
        handleAs: "json",
        //content: {uid:input.uid},
        load: function(data) {
            if (data.status == 0) {
                alert(data.errorMessage);
                return;
            }

            data = data.following
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
        url: "api/user/" + input.uid + "/getFollower",
        handleAs: "json",
        //content: {uid:input.uid},
        load: function(data) {
            if (data.status == 0) {
                alert(data.errorMessage);
                return;
            }

            data = data.followers;
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
        url: "api/user/" + id + "/unfollow",
        handleAs: "json",
        //content: {id:id},
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
        url: "/api/user/" + id + "/follow",
        handleAs: "json",
        //content: {id:id},
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