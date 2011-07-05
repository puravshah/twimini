package sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import sample.model.FollowModel;

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

    public static FollowModel getFollower(String uid) throws Exception{

        FollowModel follower = db.queryForObject("SELECT * FROM follow inner join user on user.uid=follow.following  WHERE follow.uid = ? and  end is not null",FollowModel.rowMapper, uid);

        return follower;
    }







}
