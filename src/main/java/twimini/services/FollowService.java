package twimini.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import twimini.model.UserModel;

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
    public FollowService(SimpleJdbcTemplate db) {
        this.db = db;
    }

    public int removeFollowing(String uid, String id) throws Exception {
        return db.update("UPDATE follow SET end = now() WHERE uid = ? AND following = ? AND end IS NULL", uid, id);
    }

    public UserModel addFollowing(String uid, String id) throws Exception {
        UserModel ret = db.queryForObject("SELECT uid, name, email FROM user WHERE uid = ?", UserModel.rowMapper2, id);
        db.update("INSERT INTO follow values(?, ?, now(), NULL)", uid, id);
        return ret;
    }

    public List<UserModel> getFollowing(String uid) throws Exception {
        List<UserModel> following = db.query("SELECT user.uid, name, email FROM follow INNER JOIN user ON user.uid = following WHERE follow.uid = ? AND end IS NULL", UserModel.rowMapper2, uid);
        return following;
    }

    public List<UserModel> getFollowing2(String uid, String user, String start, String count) throws Exception {
        List<UserModel> following = db.query("SELECT uid, name, email, 1 AS status FROM user WHERE uid IN (SELECT following FROM follow WHERE uid = ? AND end IS NULL AND following IN (SELECT following FROM follow WHERE uid = ? AND end IS NULL)) UNION " +
                "SELECT uid, name, email, 0 AS status FROM user WHERE uid IN (SELECT following FROM follow WHERE uid = ? AND end IS NULL AND following NOT IN (SELECT following FROM follow WHERE uid = ? AND end IS NULL)) LIMIT ?, ?", UserModel.rowMapper3, uid, user, uid, user, Integer.parseInt(start), Integer.parseInt(count));
        return following;
    }

    public List<UserModel> getFollower2(String uid, String user, String start, String count) throws Exception {
        List<UserModel> follower = db.query("SELECT uid, name, email, 1 AS status FROM user WHERE uid IN (SELECT uid FROM follow WHERE following = ? AND end IS NULL AND uid in (SELECT following FROM follow WHERE uid = ? AND end IS NULL)) UNION " +
                "SELECT uid, name, email, 0 AS status FROM user WHERE uid IN (SELECT uid FROM follow WHERE following = ? AND end IS NULL AND uid NOT IN (SELECT following FROM follow WHERE uid = ? AND end IS NULL)) LIMIT ?, ?", UserModel.rowMapper3, uid, user, uid, user, Integer.parseInt(start), Integer.parseInt(count));
        return follower;
    }

    public List<UserModel> getFollower(String uid) throws Exception {
        List<UserModel> follower = db.query("SELECT user.uid, name, email FROM follow INNER JOIN user ON user.uid = follow.uid WHERE follow.following = ? AND end IS NULL", UserModel.rowMapper2, uid);
        return follower;
    }

    public int getFollowingCount(String uid) {
        return db.queryForInt("SELECT count(following) FROM follow WHERE uid = ? AND end IS NULL", uid);
    }

    public int getFollowerCount(String uid) {
        return db.queryForInt("SELECT count(uid) FROM follow WHERE following = ? AND end is NULL", uid);
    }

    public static List<UserModel> getFollowerWithoutLogin(String uid, String start, String count) {
        return db.query("SELECT user.uid, name, email, 2 as status FROM follow INNER JOIN user ON user.uid = follow.uid WHERE follow.following = ? AND end IS NULL LIMIT ?, ?", UserModel.rowMapper3, uid, Integer.parseInt(start), Integer.parseInt(count));
    }

    public static List<UserModel> getFollowingWithoutLogin(String uid, String start, String count) {
        return db.query("SELECT user.uid, name, email, 2 as status FROM follow INNER JOIN user ON user.uid = following WHERE follow.uid = ? AND end IS NULL LIMIT ?, ?", UserModel.rowMapper3, uid, Integer.parseInt(start), Integer.parseInt(count));
    }
}
