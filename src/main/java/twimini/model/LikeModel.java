package twimini.model;

import org.springframework.jdbc.core.RowMapper;

import javax.tools.JavaCompiler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.sql.Timestamp;
/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 11/8/11
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class LikeModel {
    private int uid;
    private long pid;
    private long timestamp;

    public static RowMapper<LikeModel> rowmapper= new RowMapper<LikeModel>() {
        @Override
        public LikeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new LikeModel(rs);  //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    public LikeModel(ResultSet resultSet) throws SQLException{
       uid=resultSet.getInt("uid");
       pid=resultSet.getLong("pid");
       timestamp=((Timestamp)resultSet.getObject("timestamp")).getTime();

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
