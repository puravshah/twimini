package twimini.model;

import org.springframework.jdbc.core.RowMapper;

import javax.tools.JavaCompiler;
import java.security.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
 import java.sql.*;
/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 7/7/11
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TweetWrapper {
    private String name;//, tweet, timestamp;
    //private int pid, uid;
    private TweetModel tweet;

    private String status;

    public static RowMapper<TweetWrapper> rowMapper = new RowMapper<TweetWrapper>() {
        @Override
        public TweetWrapper mapRow(ResultSet resultSet, int i) throws SQLException {
            return new TweetWrapper(resultSet);
        }
    };

    TweetWrapper() {
    }

    TweetWrapper(ResultSet rs) throws SQLException {
        tweet = new TweetModel(rs.getInt("pid"), rs.getInt("uid"), rs.getString("tweet"), ((java.sql.Timestamp)rs.getObject("timestamp")).getTime());
        name = rs.getString("name");
        status =rs.getString("status");
        System.out.println(status);
    }

    /*public int getPid() {
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
    }*/

    public String getName() {
        return name;
    }

    public TweetModel getTweet() {
        return tweet;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    /*public void setPid(int pid) {
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
    }*/

    public void setName(String name) {
        this.name = name;
    }

    public void setTweet(TweetModel tweet) {
        this.tweet = tweet;
    }
}
