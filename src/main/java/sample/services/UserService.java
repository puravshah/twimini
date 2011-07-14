package sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final ThreadLocal<Long> userID;
    private static SimpleJdbcTemplate db;

    @Autowired
    public UserService(@Qualifier("userID") ThreadLocal<Long> userID ,SimpleJdbcTemplate db) {
        this.db = db;
        this.userID=userID;
    }

    public UserModel addUser(String name, String email, String password) throws Exception{
        db.update("INSERT INTO user(name,email, password, timestamp) " +
                  "values (?, ?, ?, now())", name, email, password);
        return db.queryForObject("SELECT * FROM user WHERE email = ?", UserModel.rowMapper, email);
    }

    public  UserModel getUser() throws  Exception
    {
        return db.queryForObject("SELECT * FROM user where uid= ?", UserModel.rowMapper,userID.get());
    }

    public  UserModel getUser(String email, String password) throws Exception {
        return db.queryForObject("SELECT * FROM user WHERE email = ? and password = ?", UserModel.rowMapper, email, password);
    }
}
