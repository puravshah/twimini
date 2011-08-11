function setInnerText(element, text) {
    if (document.all) element.innerText = text;
    else element.textContent = text;
}

function getInnerText(element) {
    if (document.all) return element.innerText;
    return element.textContent;
}

function checkLoginEmpty() {
    return true;
}

function inviteFriends() {
    var email = dojo.byId("email").value;
    dojo.xhrPost(
        {
            url:"/invite",
            handleAs:"json",
            content:{email:email},
            load:function(data) {
                if (data.status == 1) {
                    dojo.byId("error-msg").innerHTML = "mail sent successfully";
                    dojo.byId("email").value = "";
                }
                else {
                    dojo.byId("error-msg").innerHTML = "error sending mail";
                }
            },
            error:function(data) {
                dojo.byId("error-msg").innerHTML = "error sending mail";
            }
        }
    )
}

function checkLoginIfEmpty() {
    var email = dojo.byId("email").value;
    var password = dojo.byId("password").value;
    if ((email == null) || (email == "")) {
        dojo.byId("error-Msg").innerHTML = "email cannot be empty";
        return false;
    }
    else if ((password == null) || (password == "")) {
        dojo.byId("error-Msg").innerHTML = "password cannot be empty";
        return false;
    }
    return true;
}

function clearError() {
    dojo.byId("error-msg").innerHTML = "";

}

function checkSignupEmpty() {
    var name = dojo.byId("name").value;
    //alert(name);
    var email = dojo.byId("email").value;
    var password = dojo.byId("password").value;
    var cpassword = dojo.byId("cpassword").value;

    if ((name == null) || (name == "")) {
        window.location = "/signup";
        return false;
    }
    else if ((email == null) || (email == "")) {
        window.location = "/signup";
        return false;
    }
    else if ((password == null) || (password == "")) {
        window.location = "/signup";
        return false;
    }
    return true;
}

function checkIfEmpty() {
    var name = dojo.byId("name").value;
    var email = dojo.byId("email").value;
    var password = dojo.byId("password").value;
    var cpassword = dojo.byId("cpassword").value;
    if ((name == null) || (name == "")) {
        dojo.byId("nameMsg").innerHTML = "name cannot be empty";
        return false;
    }
    else if ((email == null) || (email == "")) {
        dojo.byId("emailMsg").innerHTML = "email cannot be empty";
        dojo.byId("nameMsg").innerHTML = "";
        return false;
    }
    else if ((password == null) || (password == "")) {
        dojo.byId("passwordMsg").innerHTML = "password cannot be empty";
        return false;
    }
    return true;
}

function resetError() {
    dojo.byId("nameMsg").innerHTML = "";
    dojo.byId("emailMsg").innerHTML = "";
    dojo.byId("passwordMsg").innerHTML = "";
    dojo.byId("cpasswordMsg").innerHTML = "";
    return true;
}


function isAlpha(name) {
    //alert(name);
    var xStr = dojo.byId(name).value;
    alert(xStr);
    var regEx = /^[a-zA-Z\ ]/;
    if (xStr.match(regEx)) {
        return true;
    }
    else {
        dojo.byId("nameMsg").innerHTML = "Only lowercase English Alphabets are allowed";
        return false;
    }
    return true;
}

function checkPassword() {
    var password = dojo.byId("password").value;
    var cpassword = dojo.byId("cpassword").value;
    if (password == cpassword) {
        /*if (password.length > 6) {
         dojo.byId("passwordMsg").innerHTML = "password should be of atleast 6 characters";
         return false;
         }*/
        return true;
    }
    return false;
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
                dojo.byId("tweet-count").value = (parseInt(dojo.byId("tweet-count")) + 1);
                //alert(dojo.byId("currentTweetCount").value);
            }
        },
        error: function(error) {
            //alert(error);
            alert('You need to login first');
        }
    });
}

function makeTabActiveOnEdit(index) {
    var ids = ['accountDiv', 'passwordDiv', 'imageDiv','cropDiv'];
    var div = dojo.byId("tab-container").getElementsByTagName("div");

    for (var i = 0; i < 3; i++) {
        {
            dojo.removeClass(div[i], "tab-active");
            if (index == 2 && i == 2) {
                dojo.style(ids[2], "display", "block");
                dojo.style(ids[3], "display", "none");
            }
            else {
                dojo.style(ids[i], "display", i == index ? "block" : "none");
            }
        }
    }
    if (index == 3) {
        dojo.style(ids[2], "display", "block");
        //dojo.style(ids[3],"display","block") ;
    }
    if (index == 3) {
        dojo.addClass(div[2], "tab-active");
    }
    else
        dojo.addClass(div[index], "tab-active");
}

function makeTabActive(index) {
    var ids = ['tweetDiv', 'followingDiv', 'followerDiv'];
    var div = dojo.byId("tab-container").getElementsByTagName("div");
    for (var i = 0; i < 3; i++) {
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
    var html = new EJS({url: 'static/ejs/followItem.ejs'}).render(data);
    dojo.place(html, "ListOfFollowing", "last");
}

function appendFollower(data) {
    var html = new EJS({url: 'static/ejs/followItem.ejs'}).render(data);
    dojo.place(html, "ListOfFollower", "last")
}

function searchFollower(search1) {
    alert(search1);
    var input = search1;
    var data = search1.searchdetails;
    for (var i = 0; i < data.length; i++) {
        item = data[i];
        appendFollowing({id:item.uid, name:item.name, email:item.email, user:input.user, status:item.status});
    }
}

function activateCropper(uid) {
    var src = dojo.byId("uidImage");//.setAttribute("src","/image/${uid}.png");
    src.src = "/image/" + uid + ".png";
    alert(dojo.byId("uidImage").getAttribute("src"));
    makeTabActiveOnEdit(3);
}

function getTweetTime(timestamp) {
    //alert(timestamp);
    var currentTime = new Date();
    var diff = Math.abs(timestamp - (new Date().getTime())) / 1000;

    var MIN = 60, HOUR = 3600, DAY = 86400;
    var out = "", temp;
    if (diff < MIN) {
        out = "Less than a minute";

    } else if (diff < 15 * MIN) {
        // less than fifteen minutes, show how many minutes
        temp = Math.round(diff / MIN);
        out = temp + " minute" + (temp == 1 ? "" : "s");
        // eg: 12 minutes
    } else if (diff < HOUR) {
        // less than an hour, round down to the nearest 5 minutes
        out = (Math.floor(diff / (5 * MIN)) * 5) + " minutes";
    } else if (diff < DAY) {
        // less than a day, just show hours
        temp = Math.round(diff / HOUR);
        out = temp + " hour" + (temp == 1 ? "" : "s");
    } else if (diff < 30 * DAY) {
        // show how many days ago
        temp = Math.round(diff / DAY);
        out = temp + " day" + (temp == 1 ? "" : "s");
    } else if (diff < 90 * DAY) {
        // more than 30 days, but less than 3 months, show the day and month
        return this.getDate() + " " + this.getShortMonth();  // see below
    } else {
        // more than three months difference, better show the year too
        return this.getDate() + " " + this.getShortMonth() + " " + this.getFullYear();
    }
    return out + " ago";
}

function getFeed(input, loadMore) {
    var start = (loadMore ? dojo.byId("currentTweetCount").value : 0);
    dojo.xhrPost({
        url: "/tweet/getFeed",
        handleAs: "json",
        content: {uid:input.uid, start:start},
        load: function(data) {
            if (data.status == 0) {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
                alert(data.errorMessage);
                return;
            }

            data = data.feed;
            makeTabActive(0);
            if(!loadMore) dojo.empty('ListOfTweets');
            
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                var tweet = filter(item.tweet.tweet);
                appendTweet({pid:item.tweet.pid, uid:item.tweet.uid, name: item.name, tweet:tweet, timestamp:item.tweet.timestamp});
            }

            dojo.style("loadMoreTweets", "display", (data.length < 10) ? "none" : "block");
            dojo.byId('currentTweetCount').value = (data.length + (loadMore ? parseInt(dojo.byId('currentTweetCount').value) : 0));
            //alert("start : " + dojo.byId('currentTweetCount').value);
        },
        error: function(error) {
            //alert('error : ' + error);
            alert(error);
        }
    });
}

function getTweets(input, loadMore) {
    var start = (loadMore ? dojo.byId("currentTweetCount").value : 0);
    dojo.xhrPost({
        url: "/tweet/getTweetList",
        handleAs: "json",
        content: {uid:input.uid, start:start},
        load: function(data) {
            if (data.status == 0) {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
                alert(data.errorMessage);
                return;
            }

            data = data.tweets;
            makeTabActive(0);
            if(!loadMore) dojo.empty('ListOfTweets');

            for (var i = 0; i < data.length; i++) {
                item = data[i];
                var tweet = filter(item.tweet);
                appendTweet({pid:item.pid, uid:item.uid, name:input.name, tweet:tweet, timestamp:item.timestamp});
            }

            dojo.style("loadMoreTweets", "display", (data.length < 10) ? "none" : "block");
            dojo.byId('currentTweetCount').value = (data.length + (loadMore ? parseInt(dojo.byId('currentTweetCount').value) : 0));
            //alert("start : " + dojo.byId('currentTweetCount').value);
        },
        error: function(error) {
            alert('error : ' + error);
        }
    });
}

function getFollowing(input, loadMore) {
    var start = (loadMore ? dojo.byId('currentFollowingCount').value : 0);
    dojo.xhrPost({
        url: "/user/getFollowing",
        handleAs: "json",
        content: {uid:input.uid, start:start},
        load: function(data) {
            if (data.status == 0) {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
                alert(data.errorMessage);
                return;
            }

            data = data.following
            makeTabActive(1);
            if(!loadMore) dojo.empty('ListOfFollowing');

            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollowing({id:item.uid, name:item.name, email:item.email, user:input.user, status:item.status});
            }

            //setInnerText(dojo.byId("following-count"), data.length);
            dojo.style("loadMoreFollowing", "display", (data.length < 10) ? "none" : "block");
            dojo.byId('currentFollowingCount').value = (data.length + (loadMore ? parseInt(dojo.byId('currentFollowingCount').value) : 0));
            //alert("start : " + dojo.byId('currentFollowingCount').value);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function getFollowers(input, loadMore) {
    var start = (loadMore ? dojo.byId('currentFollowersCount').value : 0);
    dojo.xhrPost({
        url: "/user/getFollowers",
        handleAs: "json",
        content: {uid:input.uid, start:start},
        load: function(data) {
            if (data.status == 0) {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
                alert(data.errorMessage);
                return;
            }

            data = data.followers;
            makeTabActive(2);
            if(!loadMore) dojo.empty('ListOfFollower');

            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollower({id:item.uid, name:item.name, email:item.email, user:input.user, status:item.status});
            }

            //setInnerText(dojo.byId("follower-count"), data.length);
            dojo.style("loadMoreFollowers", "display", (data.length < 10) ? "none" : "block");
            dojo.byId('currentFollowersCount').value = (data.length + (loadMore ? parseInt(dojo.byId('currentFollowersCount').value) : 0));
            //alert("start : " + dojo.byId('currentFollowersCount').value);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function search(input, loadMore) {
    var start = (loadMore ? dojo.byId('currentSearchCount').value : 0), count = dojo.byId('currentSearchCountValue').value;
    dojo.xhrPost({
        url: "/searchMore",
        handleAs: 'json',
        content: {query: input.query, start: start, count: count},
        load: function(data) {
            if (data.status == 0) {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
                alert(data.errorMessage);
                return;
            }

            data = data.searchResults;
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollowing({id:item.uid, name:item.name, email:item.email, user:input.user, status:item.status});
            }

            dojo.style("loadMoreSearch", "display", (data.length < count) ? "none" : "block");
            dojo.byId('currentSearchCount').value = (data.length + (loadMore ? parseInt(dojo.byId('currentSearchCount').value) : 0));
            //alert("start : " + dojo.byId('currentSearchCount').value);
        },
        error: function(error) {
            alert(error);
        }
    });
}

/*function loadMoreFeed(input) {
    var start = dojo.byId("currentTweetCount").value;
    dojo.xhrPost({
        url: "/tweet/getFeed",
        handleAs: "json",
        content: {uid:input.uid, start:start},
        load: function(data) {
            if (data.status == 0) {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
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
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
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

function loadMoreFollowing(input) {
    var start = dojo.byId('currentFollowingCount').value;
    dojo.xhrPost({
        url: "/user/getFollowing",
        handleAs: "json",
        content: {uid:input.uid, start:start},
        load: function(data) {
            if (data.status == 0) {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
                alert(data.errorMessage);
                return;
            }

            data = data.following;
            makeTabActive(1);

            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollowing({id:item.uid, name:item.name, email:item.email, user:input.user, status:item.status});
            }

            setInnerText(dojo.byId("following-count"), data.length);
            dojo.style("loadMoreFollowing", "display", (data.length < 10) ? "none" : "block");
            dojo.byId('currentFollowingCount').value = (data.length + parseInt(dojo.byId('currentFollowingCount').value));
            //alert("start : " + dojo.byId('currentFollowingCount').value);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function loadMoreFollowers(input) {
    var start = dojo.byId('currentFollowingCount').value;
    dojo.xhrPost({
        url: "/user/getFollowers",
        handleAs: "json",
        content: {uid:input.uid, start:start},
        load: function(data) {
            if (data.status == 0) {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
                alert(data.errorMessage);
                return;
            }

            data = data.following;
            makeTabActive(1);

            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollowing({id:item.uid, name:item.name, email:item.email, user:input.user, status:item.status});
            }

            setInnerText(dojo.byId("followers-count"), data.length);
            dojo.style("loadMoreFollowers", "display", (data.length < 10) ? "none" : "block");
            dojo.byId('currentFollowersCount').value = (data.length + parseInt(dojo.byId('currentFollowersCount').value));
            //alert("start : " + dojo.byId('currentFollowersCount').value);
        },
        error: function(error) {
            alert(error);
        }
    });
}

function loadMoreSearchResults(input) {
    var start = dojo.byId('currentSearchCount').value, count = dojo.byId('currentSearchCountValue').value;
    dojo.xhrPost({
        url: "/searchMore",
        handleAs: 'json',
        content: {query: input.query, start: start, count: count},
        load: function(data) {
            if (data.status == 0) {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
                alert(data.errorMessage);
                return;
            }

            data = data.searchResults;
            for (var i = 0; i < data.length; i++) {
                item = data[i];
                appendFollowing({id:item.uid, name:item.name, email:item.email, user:input.user, status:item.status});
            }

            dojo.style("loadMoreSearch", "display", (data.length < count) ? "none" : "block");
            dojo.byId('currentSearchCount').value = (data.length + parseInt(dojo.byId('currentSearchCount').value));
            //alert("start : " + dojo.byId('currentFollowersCount').value);
        },
        error: function(error) {
            alert(error);
        }
    });
}*/

function userAction(button, id) {
    if (getInnerText(button) === 'Follow') follow(button, id);
    else {
        /*dojo.style(button, "background-color", "#c80000");
         dojo.style(button, "border-top-color", "#c80000");*/
        unfollow(button, id);
    }
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
            else {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
                alert(data.errorMessage);
            }
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
            else {
                if (data.errorMessage == 'Invalid apikey') {
                    window.location = "/";
                    return;
                }
                alert(data.errorMessage);
            }
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
            if (data) {
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
    alert("password : " + password + ", cpassword : " + cpassword);
    if (password == "" || cpassword == "") {
        dojo.byId("forgot-msg").innerHTML = "<span>Please fill out all the fields</span>";
        return false;
    }
    if (password != cpassword) {
        dojo.byId("forgot-msg").innerHTML = "<span>The passwords don't match</span>";
        return false;
    }

    dojo.xhrPost({
        url: "/reset",
        handleAs: "json",
        content: {password: password, cpassword: cpassword, uid: uid},
        load: function(data) {
            if (data) {
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

function toggleDropdown() {
    var style = dojo.style('dropdown', "display");
    dojo.style('dropdown', 'display', style == 'block' ? 'none' : 'block');
    if (style == 'none') dojo.byId('dropdown-text').style.backgroundColor = '#2F2F2F';
    else dojo.byId('dropdown-text').removeAttribute('style');
}

function toggleLoginDropdown() {
    var style = dojo.style('login-dropdown', 'display');
    dojo.style('login-dropdown', 'display', style == 'block' ? 'none' : 'block');
    if (style == 'none') dojo.byId('login-text').style.backgroundColor = '#2F2F2F';
    else dojo.byId('login-text').removeAttribute('style');
}

function showResetFields() {
    dojo.style('reset-fields-container', 'display', 'block');
    dojo.style('reset-show-text', 'display', 'none');
}

function changeButtonText(button) {
    if (getInnerText(button) === 'Following') {
        setInnerText(button, "Unfollow");
        /*dojo.style(button, 'background-color', '#ff1818');
         dojo.style(button, 'border-top-color', '#ff1818');*/
    }
}

function restoreOriginal(button) {
    if (getInnerText(button) === 'Unfollow') {
        setInnerText(button, "Following");
        /*dojo.style(button, 'background-color', '#12c91e');
         dojo.style(button, 'border-top-color', '#12c91e');*/
    }
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
