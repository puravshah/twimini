package sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
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
public class UserService {
    private final ThreadLocal<Long> userID;
    private static SimpleJdbcTemplate db;

    @Autowired
    public UserService(@Qualifier("userID") ThreadLocal<Long> userID ,SimpleJdbcTemplate db) {
        this.db = db;
        this.userID = userID;
    }

    public UserModel addUser(String name, String email, String password) throws Exception {
        db.update("INSERT INTO user(name,email, password, timestamp) values (?, ?, ?, now())", name, email, password);
        return db.queryForObject("SELECT * FROM user WHERE email = ?", UserModel.rowMapper, email);
    }

    public UserModel getUser(String uid) throws  Exception {
        int status;

        try {
            int temporaryUid = db.queryForInt("SELECT uid FROM follow WHERE uid = ? and following = ? AND end IS NULL", userID.get(), uid);
            status = 1;
        }
        catch (Exception e) {
            status = 0;
        }
        return db.queryForObject("SELECT *, ? AS status FROM user WHERE uid = ?", UserModel.rowMapper3, status, uid);
    }


    public UserModel getUser(String email, String password) throws Exception {
        return db.queryForObject("SELECT * FROM user WHERE email = ? and password = ?", UserModel.rowMapper, email, password);
    }

    public List<UserModel> getSearch(String query) throws Exception {
        query = "%" + query + "%";
        return db.query("SELECT DISTINCT * FROM user WHERE name like ? or email like ?", UserModel.rowMapper, query, query);
    }

    public static List<UserModel> getInactiveUser() throws  Exception {
        db.update("UPDATE user SET  isActivated=3 Where isActivated=0");
        return db.query("SELECT * FROM user where isActivated=3", UserModel.rowMapper);
    }

    public static void  setToPartialState() {
        //To change body of created methods use File | Settings | File Templates.
        db.update("UPDATE user SET  isActivated=2 Where isActivated=3");
    }

    public void setIsActivated(String uid) {
        //To change body of created methods use File | Settings | File Templates.
        db.update("UPDATE  user SET iSActivated=1 WHERE uid=?",uid);
    }
}
