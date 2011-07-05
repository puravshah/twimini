package sample.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static final RowMapper <TweetModel> rowMapper = new RowMapper<TweetModel>() {
        @Override
        public TweetModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return new TweetModel(resultSet);
        }
    };

    public TweetModel(){}
    public TweetModel(ResultSet rs) throws SQLException {
        pid = rs.getInt("pid");
        uid = rs.getInt("uid");
        tweet = rs.getString("tweet");
        timestamp = rs.getString("timestamp");
    }

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
