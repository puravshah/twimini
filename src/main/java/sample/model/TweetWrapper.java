package sample.model;

import org.springframework.jdbc.core.RowMapper;

import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 7/7/11
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TweetWrapper {
    private String name, tweet, timestamp;
    private int pid, uid;

    public static RowMapper <TweetWrapper> rowMapper = new RowMapper<TweetWrapper>() {
        @Override
        public TweetWrapper mapRow(ResultSet resultSet, int i) throws SQLException {
            return new TweetWrapper(resultSet);
        }
    };

    TweetWrapper(){}
    TweetWrapper(ResultSet rs) throws SQLException {
        pid = rs.getInt("pid");
        uid = rs.getInt("uid");
        tweet = rs.getString("tweet");
        timestamp = rs.getString("timestamp");
        name = rs.getString("name");
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

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }
}
