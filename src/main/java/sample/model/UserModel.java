package sample.model;

import org.springframework.jdbc.core.RowMapper;

import java.security.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 7/4/11
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserModel {
    private String email, password, firstName, lastName, timestamp;
    private int uid;

    public static final RowMapper <UserModel> rowMapper = new RowMapper<UserModel>() {
        @Override
        public UserModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return new UserModel(resultSet);
        }
    };

    public static final RowMapper <UserModel> rowMapper2 = new RowMapper<UserModel>() {
        @Override
        public UserModel mapRow(ResultSet resultSet, int i) throws SQLException {
            UserModel u = new UserModel();
            u.setFirstName(resultSet.getString("firstname"));
            u.setLastName(resultSet.getString("lastname"));
            u.setEmail(resultSet.getString("email"));
            u.setUid(resultSet.getInt("uid"));
            return u;
        }
    };

    public UserModel(){}
    public UserModel(ResultSet rs) throws SQLException{
        email = rs.getString("email");
        password = rs.getString("password");
        firstName = rs.getString("firstname");
        lastName = rs.getString("lastname");
        timestamp = rs.getString("timestamp");
        uid = rs.getInt("uid");
    }

    public UserModel(int uid, String email, String password, String firstName, String lastName, String timestamp) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timestamp = timestamp;
    }

    public int getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
