function createTweet(datas) {
	name = datas.name;
    $.ajax({
		url: "/tweet/create",
        type: "POST",
        data: "tweet=" + document.getElementById("tweet-box").value,
        success: function(data) {
			if(data.status === "0") alert("Unable to add tweet: " + data.errorMsg);
            else {
                data.name = name;
                prependTweet(data);
            }

			data.name = name;
            document.getElementById("tweetBox").value="";
            prependTweet(data);
			}

		});

}

function prependTweet(data) {
	var html = new EJS({url: '/static/tweetItem.ejs'}).render(data);
    var tweetItemLi = $(html);
    $('#ListOfTweets').prepend(tweetItemLi);
}

function appendFollowing(data) {
	var html = new EJS({url: 'static/followingItem.ejs'}).render(data);
    var followingItemLi = $(html);
    $('#ListOfFollowing').append(followingItemLi);
}

function appendFollower(data) {
	var html = new EJS({url: 'static/followerItem.ejs'}).render(data);
    var followerItemLi = $(html);
    $('#ListOfFollower').append(followerItemLi);
}

function getFeed() {
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

function getFollowing() {
	$('#tweetDiv').hide();
	$('#followingDiv').show();
	$('#followerDiv').hide();

    var divs = document.getElementById("tab-container").getElementsByTagName("div");
    var newClass = "span-2 tab tab-active";
    divs[0].setAttribute("class", "span-2 tab");
    divs[1].setAttribute("class", newClass);
    divs[2].setAttribute("class", "span-2 tab last");
}

function getFollowers(datas) {
	/*$.ajax({
    url: "/user/follower.json",
    type: "POST",
    data: "uid=" + datas.uid,
    success: function(data) {
		$('#tweetDiv').hide();
		$('#followingDiv').hide();
		$('#followerDiv').show();
		}
	});*/
    $('#tweetDiv').hide();
    $('#followingDiv').hide();
    $('#followerDiv').show();

    var divs = document.getElementById("tab-container").getElementsByTagName("div");
    var newClass = "span-2 tab last tab-active";
    divs[0].setAttribute("class", "span-2 tab");
    divs[1].setAttribute("class", "span-2 tab");
    divs[2].setAttribute("class", newClass);
}

function unfollow(id) {
	$.ajax({
    url: "/user/unfollow",
    type: "POST",
    data: "id=" + id,
    success: function(data) {
		    if(data.status === "1") $('#followingItem_' + id).remove();
            else alert(data.errorMsg);
        }
	});
}

function follow(id) {
	$.ajax({
    url: "/user/follow",
    type: "POST",
    data: "id=" + id,
    success: function(data) {
		    if(data.status === "1") appendFollowing(data);
            else alert(data.errorMsg);
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
    for(var i = data.length() - 1; i >= 0; i--)
    {
        var ok = 0;
        for(var j = valid.length() - 1; j >= 0; j--)
            if(valid[j] == data[i]) {
                ok = 1;
                break;
            }
        if(ok == 0) return false;
    }

    return true;
}