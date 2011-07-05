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

        private int uid;
        private String timestamp;
        private int following;
        private String start;
        private String end;

        public static final RowMapper<FollowModel> rowMapper = new RowMapper<FollowModel>() {
            @Override
            public FollowModel mapRow(ResultSet resultSet, int i) throws SQLException {
                return new FollowModel(resultSet);
            }
        };

        public FollowModel(){}
        public FollowModel(ResultSet rs) throws SQLException {
            uid = rs.getInt("follow.uid");
            //firstName=rs.getString("user.firstName");
            //lastName=rs.getString("user.lastName");
          }

        public FollowModel(int folowing, int uid, String end, String start) {
         }


        public int getUid() {
            return uid;
        }


        public void setUid(int uid) {
            this.uid = uid;
        }


    }




