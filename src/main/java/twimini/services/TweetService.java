package twimini.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import twimini.model.TweetModel;
import twimini.model.TweetWrapper;

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
    public TweetService(SimpleJdbcTemplate db) {
        this.db = db;
    }

    public TweetModel addTweet(String uid, String tweet) throws Exception {
        db.update("INSERT INTO post(uid, tweet, timestamp) values(?, ?, now())", uid, tweet);
        return db.queryForObject("SELECT * ,'false' as status FROM post WHERE pid = (SELECT MAX(pid) FROM post)", TweetModel.rowMapper);
    }

    public TweetModel getTweetDetails(String pid) throws Exception {
        return db.queryForObject("SELECT * ,'false' as status FROM post WHERE pid = ?", TweetModel.rowMapper, pid);
    }

    public List<TweetWrapper> getFeed(String uid, String start, String count) throws Exception {
        List<TweetWrapper> l = db.query("(SELECT u.uid,name, pid, tweet, x.timestamp,'true' as status FROM post x, user u " +
                "WHERE u.uid = x.uid AND x.pid IN " +
                "(SELECT pid FROM post p WHERE p.pid in (SELECT likes.pid FROM likes WHERE likes.uid =?) AND p.uid IN " +
                "(SELECT following FROM follow f WHERE f.uid = ? AND p.timestamp BETWEEN start AND IFNULL(end, now()))"+
                " UNION " +
                "SELECT pid FROM post WHERE post.uid = ? AND post.pid in ( SELECT likes.pid FROM likes WHERE likes.uid =?)))"+
                "UNION " +
                "(SELECT u.uid, name, pid,tweet, x.timestamp,'false' as status FROM post x, user u " +
                "WHERE u.uid = x.uid AND x.pid IN " +
                "(SELECT pid FROM post p WHERE p.pid NOT in (SELECT likes.pid FROM likes WHERE likes.uid =?) AND p.uid IN " +
                "(SELECT following FROM follow f WHERE f.uid = ? AND p.timestamp BETWEEN start AND IFNULL(end, now())) " +
                "UNION " +
                "SELECT pid FROM post WHERE post.uid = ? AND post.pid NOT in ( SELECT likes.pid FROM likes WHERE likes.uid =?))) order by 5 desc   LIMIT ?, ?"
                , TweetWrapper.rowMapper,uid,uid, uid, uid,uid,uid,uid, uid,Integer.parseInt(start),Integer.parseInt(count));
        return l;
    }

    public List<TweetModel> getTweetList(String uid,String id, String start, String count) throws Exception {
        List<TweetModel> l = db.query("SELECT *,'true' as status FROM post WHERE uid = ?" +
                                      " AND post.pid in (SELECT likes.pid FROM likes WHERE likes.uid=?) " +
                                      "UNION " +
                                      "SELECT *,'false' as status FROM post WHERE uid=? AND " +
                                      "post.pid NOT in (SELECT likes.pid FROM likes WHERE likes.uid=?)" +
                                      "ORDER BY pid DESC LIMIT ?, ?", TweetModel.rowMapper,uid,id,uid,id, Integer.parseInt(start), Integer.parseInt(count));
        return l;
    }

    public int getTweetCount(String uid) {
        return db.queryForInt("SELECT count(pid) FROM post WHERE uid = ?", uid);
    }
}
