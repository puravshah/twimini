function createTweet(datas) {
	name = datas.name;
    $.ajax({
		url: "/tweet/create.json",
        type: "POST",
        data: "tweet=" + document.getElementById("tweetBox").value,
        success: function(data) {
			data.name = name;
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
	$('#tweetDiv2').show();
    $('#followingDiv2').hide();
    $('#followerDiv2').hide();
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
    url: "/user/unfollow.json",
    type: "POST",
    data: "unfollowId=" + id,
    success: function(data) {
		$('#followingItem_' + id).remove();
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