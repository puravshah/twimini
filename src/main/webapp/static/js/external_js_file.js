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

function getFeed() {
	$('#tweetDiv2').show();
    $('#followingDiv2').hide();
    $('#followerDiv2').hide();
}

function getTweets() {
	$('#tweetDiv').show();
	$('#followingDiv').hide();
	$('#followerDiv').hide();
}

function getFollowing() {
	$('#tweetDiv').hide();
	$('#followingDiv').show();
	$('#followerDiv').hide();
}

function getFollowers(datas) {
	$.ajax({
    url: "/user/follower.json",
    type: "POST",
    data: "uid=" + datas.uid,
    success: function(data) {
		$('#tweetDiv').hide();
		$('#followingDiv').hide();
		$('#followerDiv').show();
		}
	});
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