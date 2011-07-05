package sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import sample.model.FollowModel;
import sample.model.UserModel;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh.k
 * Date: 5/7/11
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FollowService {
          private static SimpleJdbcTemplate db;

    @Autowired
    public FollowService(SimpleJdbcTemplate db) {this.db = db;}

    public static List<UserModel> getFollower(String uid) throws Exception{

        List<UserModel> follower = db.query("SELECT user.uid,user.firstname,user.lastname,user.email  FROM follow inner join user on user.uid=follow.following  WHERE follow.uid = ? and  end is not null",UserModel.rowMapper2, uid);

        return follower;
    }

    public static List<UserModel> getFollowing(String uid) throws Exception{

            List<UserModel> follower = db.query("SELECT user.uid FROM follow inner join user on user.uid=follow.uid  WHERE follow.following = ? and  end is not null",UserModel.rowMapper2, uid);

            return follower;
        }






}
