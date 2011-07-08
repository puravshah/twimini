package sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import sample.model.TweetModel;
import sample.model.TweetWrapper;
import sample.model.UserModel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 7/4/11
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class TweetService {

    private static SimpleJdbcTemplate db;
    @Autowired
    public TweetService(SimpleJdbcTemplate db) {this.db = db;}

    public static TweetModel addTweet(String uid, String tweet) throws Exception {
        db.update("INSERT INTO post(uid, tweet, timestamp) values(?, ?, now())", uid, tweet);
        TweetModel t = db.queryForObject("SELECT * FROM post WHERE pid = (SELECT MAX(pid) FROM post)", TweetModel.rowMapper);
        return t;
    }

    public static List<TweetModel> getTweetList(String uid) throws Exception {
        List <TweetModel> l = db.query("SELECT * FROM post WHERE uid = ?", TweetModel.rowMapper, uid);
        return l;
    }

    public static List<TweetWrapper> getFeed(String uid) throws Exception {
        List <TweetWrapper> l = db.query("SELECT u.uid, name, pid, tweet, x.timestamp FROM post x, user u " +
                "WHERE u.uid = x.uid AND x.pid IN " +
                "(SELECT pid FROM post p WHERE p.uid IN " +
                "(SELECT following FROM follow f WHERE f.uid = ? AND p.timestamp BETWEEN start AND IFNULL(end, now())) UNION SELECT pid FROM post WHERE post.uid = ?) ORDER BY x.timestamp", TweetWrapper.rowMapper, uid, uid);
        return l;
    }

    public static int getTweetCount(String uid) {
        return db.queryForInt("SELECT count(pid) FROM post WHERE uid = ?", uid);
    }
}
