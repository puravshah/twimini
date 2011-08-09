package twimini.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 8/1/11
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class APIKEYService {
    private final ThreadLocal<Long> userID;
    private static SimpleJdbcTemplate db;

    @Autowired
    public APIKEYService(@Qualifier("userID") ThreadLocal<Long> userID, SimpleJdbcTemplate db) {
        this.db = db;
        this.userID = userID;
    }

    public static String getAPIKEY(int uid) {
        try {
            return db.queryForObject("SELECT apikey FROM user_apikey WHERE uid = ?", String.class, uid);
        } catch (Exception e) {
            String APIKEY = UUID.randomUUID().toString();
            db.update("INSERT INTO user_apikey values(?, ?, now())", uid, APIKEY);
            return APIKEY;
        }
    }

    public static int updateLastUsedTimestamp(String apikey) {
        return db.update("UPDATE user_apikey SET last_used = now() WHERE apikey = ?", apikey);
    }

    public static String getUid(String apikey) {
        if(apikey == null || apikey.equals(null) || apikey == "") return null;
        String uid = "" + db.queryForInt("SELECT uid FROM user_apikey WHERE apikey = ?", apikey);
        updateLastUsedTimestamp(apikey);
        return uid;
    }

    public static int removeAPIKEY(int uid) {
        try {
            return db.update("DELETE FROM user_apikey WHERE uid = ?", uid);
        } catch (Exception e) {
            return 0;
        }
    }
}
