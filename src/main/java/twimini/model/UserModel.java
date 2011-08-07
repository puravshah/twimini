package twimini.model;

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
    private String email;
    private String name;
    private String isActivated; //, password, timestamp;
    private int uid, status;


    public static final RowMapper<UserModel> rowMapper = new RowMapper<UserModel>() {
        @Override
        public UserModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return new UserModel(resultSet);
        }
    };

    public static final RowMapper<String> rowMapper4= new RowMapper<String>() {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("password");
        }
    };


    public static final RowMapper<UserModel> rowMapper2 = new RowMapper<UserModel>() {
        @Override
        public UserModel mapRow(ResultSet resultSet, int i) throws SQLException {
            UserModel u = new UserModel();
            u.setName(resultSet.getString("name"));
            u.setEmail(resultSet.getString("email"));
            u.setUid(resultSet.getInt("uid"));
            return u;
        }
    };

    public static final RowMapper<UserModel> rowMapper3 = new RowMapper<UserModel>() {
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

    public UserModel() {
    }

    public UserModel(ResultSet rs) throws SQLException {
        email = rs.getString("email");
        name = rs.getString("name");
        uid = rs.getInt("uid");
        isActivated=rs.getString("isActivated");
        /*password = rs.getString("password");
        timestamp = rs.getString("timestamp");*/
    }

    public int getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public String getActivated() {
        return isActivated;
    }

    public void setActivated(String activated) {
        isActivated = activated;
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

    public void setName(String name) {
        this.name = name;
    }

    /*public String getPassword() {
        return password;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }*/
}
