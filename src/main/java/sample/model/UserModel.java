package sample.model;

import org.springframework.jdbc.core.RowMapper;
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
    private String email, password, name, timestamp;
    private int uid, status;

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
            u.setName(resultSet.getString("name"));
            u.setEmail(resultSet.getString("email"));
            u.setUid(resultSet.getInt("uid"));
            return u;
        }
    };

    public static final RowMapper <UserModel> rowMapper3 = new RowMapper<UserModel>() {
        @Override
        public UserModel mapRow(ResultSet resultSet, int i) throws SQLException {
            UserModel u = new UserModel();
            u.setName(resultSet.getString("name"));
            u.setEmail(resultSet.getString("email"));
            u.setUid(resultSet.getInt("uid"));
            u.setStatus(resultSet.getInt("status"));
            return u;
        }
    };

    public UserModel(){}
    public UserModel(ResultSet rs) throws SQLException{
        email = rs.getString("email");
        password = rs.getString("password");
        name = rs.getString("name");
        timestamp = rs.getString("timestamp");
        uid = rs.getInt("uid");
    }

    public UserModel(int uid, String email, String password, String name, String timestamp) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
