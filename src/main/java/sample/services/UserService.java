package sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import sample.model.UserModel;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 7/4/11
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class UserService {
    private static SimpleJdbcTemplate db;

    @Autowired
    public UserService(SimpleJdbcTemplate db) {this.db = db;}

    public static UserModel addUser(String firstname, String lastname, String email, String password) throws Exception{
        db.update("INSERT INTO user(firstname, lastname, email, password, timestamp) " +
                  "values (?, ?, ?, ?, now())", firstname, lastname, email, password);
        return db.queryForObject("SELECT * FROM user WHERE email = ?", UserModel.rowMapper, email);
    }

    public static  UserModel getUser(int Uid) throws  Exception
    {
        return db.queryForObject("SELECT * FROM user where uid= ?", UserModel.rowMapper,Uid);

    }
    public static UserModel getUser(String email, String password) throws Exception {
        return db.queryForObject("SELECT * FROM user WHERE email = ? and password = ?", UserModel.rowMapper, email, password);
    }
}
