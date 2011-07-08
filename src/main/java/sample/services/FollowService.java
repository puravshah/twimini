package sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import sample.model.UserModel;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh.k
 * Date: 5/7/11
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FollowService {
    private static SimpleJdbcTemplate db;

    @Autowired
    public FollowService(SimpleJdbcTemplate db) {this.db = db;}

    public static List<UserModel> getFollowing(String uid) throws Exception {
        List<UserModel> following = db.query("SELECT user.uid, name, email FROM follow INNER JOIN user ON user.uid = following WHERE follow.uid = ? AND end IS NULL", UserModel.rowMapper2, uid);
        return following;
    }

    public static List<UserModel> getFollower(String uid) throws Exception {
        List<UserModel> follower = db.query("SELECT user.uid, name, email FROM follow INNER JOIN user ON user.uid = follow.uid WHERE follow.following = ? AND end IS NULL", UserModel.rowMapper2, uid);
        return follower;
    }

    public static void removeFollowing(String uid, String id) throws Exception {
        db.update("UPDATE follow SET end = now() WHERE uid = ? AND following = ? AND end IS NULL", uid, id);
    }

    public static void addFollowing(String uid, String id) throws Exception {
        db.update("INSERT INTO follow values(?, ?, now(), NULL)", uid, id);
    }

    public static int getFollowingCount(String uid) {
        return db.queryForInt("SELECT count(following) FROM follow WHERE uid = ? AND end IS NULL", uid);
    }

    public static int getFollowerCount(String uid) {
        return db.queryForInt("SELECT count(uid) FROM follow WHERE following = ? AND end is NULL", uid);
    }
}
