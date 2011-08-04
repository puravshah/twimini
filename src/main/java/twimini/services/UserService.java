package twimini.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import twimini.model.UserModel;

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
    public UserService(@Qualifier("userID") ThreadLocal<Long> userID, SimpleJdbcTemplate db) {
        this.db = db;
        this.userID = userID;
    }

    public static String getPassword(String uid) {
       return  (String)db.queryForObject("SELECT password FROM user WHERE uid=?",UserModel.rowMapper4,uid);
    }

    public UserModel addUser(String name, String email, String password) throws Exception {
        db.update("INSERT INTO user(name,email, password, timestamp) values (?, ?, ?, now())", name, email, password);
        return db.queryForObject("SELECT * FROM user WHERE email = ?", UserModel.rowMapper, email);
    }

    public UserModel getUser(String uid) throws Exception {
        /*int status = 1;
        System.out.println("user : " + userID.get());
        try {
            int temporaryUid = db.queryForInt("SELECT uid FROM follow WHERE uid = ? and following = ? AND end IS NULL", userID.get(), uid);
            status = 1;
            System.out.println("Following");
        } catch (EmptyResultDataAccessException e) {
            status = 0;
            System.out.println("Not following");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return db.queryForObject("SELECT *, 0 AS status FROM user WHERE uid = ?", UserModel.rowMapper3, uid);
    }

    public UserModel getUser2(String user, String uid) throws Exception {
        int status = 1;
        try {
            int temporaryUid = db.queryForInt("SELECT uid FROM follow WHERE uid = ? and following = ? AND end IS NULL", user, uid);
            status = 1;
        } catch (EmptyResultDataAccessException e) {
            status = 0;
        } catch (Exception e) {
            e.printStackTrace();
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

    public static List<UserModel> getInactiveUser() {
        db.update("UPDATE user SET isActivated=3 Where isActivated=0");
        return db.query("SELECT * FROM user where isActivated=3", UserModel.rowMapper);
    }

    public static void setToPartialState() {
        db.update("UPDATE user SET isActivated=2 Where isActivated=3");
    }

    public static UserModel getUserInfo(String email) {
        return db.queryForObject("SELECT * FROM user where email = ?", UserModel.rowMapper, email);
    }

    public void setIsActivated(String uid) {
        db.update("UPDATE user SET iSActivated = 1 WHERE uid = ?", uid);
    }

    public static void addToken(String token, int uid) throws Exception {
        db.update("INSERT INTO forgot_token values(?, ?, now())", token, uid);
    }

    public static int getUidFromForgotToken(String token) throws Exception {
        return db.queryForInt("SELECT uid FROM forgot_token WHERE token = ?", token);
    }

    public static void removeForgotToken(String token) throws Exception {
        db.update("DELETE FROM forgot_token WHERE token = ?", token);
    }

    public static void changePassword(String uid, String password) throws Exception {
        db.update("UPDATE user SET password = ? WHERE uid = ?", password, uid);
    }

    public static void setAccountInfo(String name, String email,String uid) {
        //To change body of created methods use File | Settings | File Templates.
        db.update("update user SET name=? ,email=? WHERE uid=? ",name,email,uid);
    }

    public static void setPassword(CharSequence newPassword,String uid) {

        db.update("update user SET password=? WHERE uid=? ",newPassword,uid);
    }
}
