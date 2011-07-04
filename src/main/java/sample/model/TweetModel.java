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
    private String tweet;

    public TweetModel(){}
    public TweetModel(int pid, int uid, String tweet) {
        this.pid = pid;
        this.uid = uid;
        this.tweet = tweet;
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

    public int getPid() {
        return pid;
    }

    public int getUid() {
        return uid;
    }

    public String getTweet() {
        return tweet;
    }
}
