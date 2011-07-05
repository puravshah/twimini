package sample.model;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 7/4/11
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class TweetModel {
    private int pid, uid;
    private String tweet, timestamp;

    public TweetModel(){}
    public TweetModel(int pid, int uid, String tweet, String timestamp) {
        this.pid = pid;
        this.uid = uid;
        this.tweet = tweet;
        this.timestamp = timestamp;
    }

    public int getPid() {
        return pid;
    }

    public int getUid() {
        return uid;
    }

    public String getTweet() {
        return tweet;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
