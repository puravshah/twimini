package sample.services;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final ThreadLocal<Long> userID;
    private static SimpleJdbcTemplate db;

    @Autowired
    public FollowService(@Qualifier("userID") ThreadLocal<Long> userID,SimpleJdbcTemplate db) {
        this.db = db;
        this.userID= userID;
     }

    public  List<UserModel> getFollowing() throws Exception {
        List<UserModel> following = db.query("SELECT user.uid, name, email FROM follow INNER JOIN user ON user.uid = following WHERE follow.uid = ? AND end IS NULL", UserModel.rowMapper2, userID.get());
        return following;
    }

    public  List<UserModel> getFollower() throws Exception {
        List<UserModel> follower = db.query("SELECT user.uid, name, email FROM follow INNER JOIN user ON user.uid = follow.uid WHERE follow.following = ? AND end IS NULL", UserModel.rowMapper2, userID.get());
        return follower;
    }

    public  void removeFollowing(String id) throws Exception {
        db.update("UPDATE follow SET end = now() WHERE uid = ? AND following = ? AND end IS NULL", userID.get(), id);
    }

    public void addFollowing(String id) throws Exception {
        db.update("INSERT INTO follow values(?, ?, now(), NULL)", userID.get(), id);
    }

    public  int getFollowingCount() {
        return db.queryForInt("SELECT count(following) FROM follow WHERE uid = ? AND end IS NULL", userID.get());
    }

    public  int getFollowerCount() {
        return db.queryForInt("SELECT count(uid) FROM follow WHERE following = ? AND end is NULL", userID.get());
    }
    public  List<UserModel> getFollowing(String uid) throws Exception {
        List<UserModel> following = db.query("SELECT user.uid, name, email FROM follow INNER JOIN user ON user.uid = following WHERE follow.uid = ? AND end IS NULL", UserModel.rowMapper2, uid);
        return following;
    }

    public  List<UserModel> getFollower(String uid) throws Exception {
        List<UserModel> follower = db.query("SELECT user.uid, name, email FROM follow INNER JOIN user ON user.uid = follow.uid WHERE follow.following = ? AND end IS NULL", UserModel.rowMapper2, uid);
        return follower;
    }

    public  int getFollowingCount(String uid) {
        return db.queryForInt("SELECT count(following) FROM follow WHERE uid = ? AND end IS NULL", uid);
    }

    public  int getFollowerCount(String uid) {
        return db.queryForInt("SELECT count(uid) FROM follow WHERE following = ? AND end is NULL", uid);
    }
}
