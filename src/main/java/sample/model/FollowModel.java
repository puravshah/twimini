package sample.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 5/7/11
 * Time: 12:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class FollowModel {
    private int uid, following;
    private String start, end;

    public static final RowMapper<FollowModel> rowMapper = new RowMapper<FollowModel>() {
        @Override
        public FollowModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return new FollowModel(resultSet);
        }
    };

    public FollowModel(){}
    public FollowModel(ResultSet rs) throws SQLException {
        uid = rs.getInt("follow.uid");
    }

    public FollowModel(int uid, int following, String start, String end) {
        this.uid = uid;
        this.following = following;
        this.start = start;
        this.end = end;
    }

    public int getUid() {
        return uid;
    }

    public int getFollowing() {
        return following;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}




