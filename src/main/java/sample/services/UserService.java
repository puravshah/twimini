package sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import sample.model.UserModel;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 7/4/11
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserService {
    private SimpleJdbcTemplate db;

    @Autowired
    public UserService(SimpleJdbcTemplate db) {this.db = db;}

    public boolean addUser(UserModel u) {
        db.update("INSERT INTO user(firstname, lastname, emailid, password, timestamp) " +
                  "values (?, ?, ?, ?, now())", u.getFirstName(), u.getLastName(), u.getEmail(), u.getPassword());

        int x = (int)db.queryForLong("mysql_insert_id()");
        System.out.println("Cur val : " + x);
        return true;
    }

    /*public UserModel getUser(String email, String password) {

    }*/
}
