package twimini.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import twimini.model.LikeModel;
import twimini.model.TweetWrapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 11/8/11
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class LikeService {
        private static SimpleJdbcTemplate db;

    @Autowired
    public LikeService(SimpleJdbcTemplate db) {
        this.db=db;
    }

    public List<TweetWrapper> getLikes(String uid,String id,String start,String count)
    {
        return  db.query("(SELECT user.uid,user.name,post.pid,post.tweet ,post.timestamp,'true' AS " +
                "status FROM post ,user " +
                "WHERE post.uid=user.uid AND post.pid " +
                "IN (" +
                "SELECT likes.pid " +
                "FROM likes " +
                "WHERE likes.uid =?" +
                "AND likes.pid IN " +
                "(SELECT likes.pid FROM likes  WHERE likes.uid =?)))" +
                "UNION " +
                "(SELECT user.uid,user.name,post.pid,post.tweet ,post.timestamp,'false' AS " +
                "status FROM post ,user " +
                "WHERE post.uid=user.uid AND post.pid " +
                "IN (" +
                "SELECT likes.pid " +
                "FROM likes " +
                "WHERE likes.uid =? " +
                "AND likes.pid NOT IN " +
                "(SELECT likes.pid FROM likes WHERE likes.uid =?)))" +
                "order by 5 Desc LIMIT ?,? ",TweetWrapper.rowMapper,uid,id,uid,id,Integer.parseInt(start),Integer.parseInt(count));
    }

    public void insertLike(String uid,String pid)
    {
          db.update("INSERT INTO likes values(?,?,now())", uid, pid);
    }

    public void deleteLike(String tweetId, String uid) {
            //To change body of created methods use File | Settings | File Templates.
        db.update("DELETE FROM likes WHERE pid =? AND uid=?",tweetId,uid);
    }
}
