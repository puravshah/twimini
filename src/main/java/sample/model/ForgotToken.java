package sample.model;

import org.springframework.jdbc.core.RowMapper;

import javax.mail.internet.HeaderTokenizer;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 7/29/11
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */

public class ForgotToken {
    private String token, timestamp;
    private int uid;

    public static RowMapper<ForgotToken> rowMapper = new RowMapper<ForgotToken>() {
        @Override
        public ForgotToken mapRow(ResultSet resultSet, int i) throws SQLException {
            return new ForgotToken(resultSet);
        }
    };

    public ForgotToken(ResultSet resultSet) throws SQLException{
        token = resultSet.getString("token");
        uid = resultSet.getInt("uid");
        timestamp = resultSet.getString("timestamp");
    }

    public String getToken() {
        return token;
    }

    public int getUid() {
        return uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
