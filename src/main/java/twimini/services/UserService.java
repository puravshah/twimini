package twimini.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import twimini.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public boolean checkPassword(String uid, String password) {
        try {
            db.queryForInt("SELECT uid FROM user WHERE uid = ? and password = ?", uid, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserModel addUser(String name, String email, String password) throws Exception {
        db.update("INSERT INTO user(name, email, password, timestamp) values (?, ?, ?, now())", name, email, password);
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

    public List<UserModel> getSearch(String query, String uid, String start, String count) throws Exception {
        if(uid == null || uid.equals("")) uid = "-1";
        query = "%" + query + "%";
        return db.query("SELECT DISTINCT uid, name, email, 1 as status FROM user WHERE (name like ? or email like ?) AND uid IN \n" +
                "(SELECT following FROM follow WHERE uid = ? AND end IS NULL) \n" +
                "UNION\n" +
                "SELECT DISTINCT uid, name, email, 0 as status FROM user WHERE (name like ? or email like ?) AND uid NOT IN \n" +
                "(SELECT following FROM follow WHERE uid = ? AND end IS NULL) LIMIT ?, ?", UserModel.rowMapper3, query, query, uid, query, query, uid, Integer.parseInt(start), Integer.parseInt(count));
    }

    public static List<UserModel> getInactiveUser() {

        String activationStatus = "email not sent";
        return db.query("SELECT * FROM user WHERE isActivated = ?", UserModel.rowMapper, activationStatus);
    }

    public static UserModel getUserInfo(String email) {
        return db.queryForObject("SELECT * FROM user WHERE email = ?", UserModel.rowMapper, email);
    }

    public static String addForgotToken(int uid) throws Exception {
        String token = UUID.randomUUID().toString();
        try {
            db.update("INSERT INTO forgot_token VALUES(?, ?, now())", uid, token);
        } catch(DuplicateKeyException e) {
            db.update("UPDATE forgot_token set token = ? WHERE uid = ?", token, uid);
        }
        return token;
    }

    public static String getUidFromForgotToken(String token) throws Exception {
        return db.queryForObject("SELECT uid FROM forgot_token WHERE token = ?", String.class, token);
    }

    public static void removeForgotToken(String token) throws Exception {
        db.update("DELETE FROM forgot_token WHERE token = ?", token);
    }

    public static int changePassword(String uid, String oldPassword, String newPassword) throws Exception {
        return db.update("UPDATE user SET password = ? WHERE uid = ? and password = ?", newPassword, uid, oldPassword);
    }

    public static int changePassword(String uid, String password) {
        return db.update("update user SET password = ? WHERE uid = ?", password, uid);
    }

    public static int setAccountInfo(String name, String email, String uid) {
        return db.update("UPDATE user SET name = ?, email = ? WHERE uid = ? ", name, email, uid);
    }

    public static void setPassword(CharSequence newPassword, String uid) {
        db.update("update user SET password = ? WHERE uid = ? ", newPassword, uid);
    }

    public static void setToNotactivated(String[] sendTo) {
        /*Set<String> ids = new HashSet<String>();
        for (String string : sendTo)
            ids.add(string);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);
        db.update("UPDATE user SET isActivated='activated' where email in ( :ids )", parameters);*/

        List <Object[]> list = new ArrayList<Object[]> ();
        for(String id: sendTo) list.add(new Object[] {id});
        db.batchUpdate("UPDATE user SET isActivated = 'not activated' WHERE email = ?", list);
    }

    public String getIsActivated(String uid) {
        return db.queryForObject("SELECT isActivated FROM user WHERE uid = ?", String.class, uid);
    }

    public String getActivationToken(String uid) {
        try {
            return db.queryForObject("SELECT token FROM activation_token WHERE uid = ?", String.class, uid);
        } catch(EmptyResultDataAccessException e) {
            addActivationToken(uid);
            return db.queryForObject("SELECT token FROM activation_token WHERE uid = ?", String.class, uid);
        }
    }

    public int addActivationToken(String uid) {
        try {
            String token = UUID.randomUUID().toString();
            return db.update("INSERT INTO activation_token VALUES(?, ?, now())", uid, token);
        } catch(Exception e) {
            System.out.println(e);
            removeActivationToken(getActivationToken(uid));
        }
        String token = UUID.randomUUID().toString();
        return db.update("INSERT INTO activation_token VALUES(?, ?, now())", uid, token);
    }

    public void setIsActivated(String token) {
        String uid = db.queryForObject("SELECT uid FROM activation_token WHERE token = ?", String.class, token);
        removeActivationToken(token);
        db.update("UPDATE user SET iSActivated = 'activated' WHERE uid = ?", uid);
    }

    private int removeActivationToken(String token) {
        return db.update("DELETE FROM activation_token WHERE token = ?", token);
    }
}
