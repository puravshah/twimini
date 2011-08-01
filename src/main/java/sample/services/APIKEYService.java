package sample.services;

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
            Map<String, Object> map = db.queryForMap("SELECT apikey FROM user_apikey WHERE uid = ?", uid);
            return (String) map.get("APIKEY");
        } catch (Exception e) {
            String APIKEY = UUID.randomUUID().toString();
            db.update("INSERT INTO user_apikey values(?, ?, now())", uid, APIKEY);
            return APIKEY;
        }
    }

    public static String getUid(String APIKEY) {
        return "" + db.queryForInt("SELECT uid FROM user_apikey WHERE apikey = ?", APIKEY);
    }

    public static int removeAPIKEY() {
        return db.update("DELETE FROM user_apikey WHERE now() - timestamp > timedelta(hours=1)");
    }

    public static int removeAPIKEY(int uid) {
        try {
            return db.update("DELETE FROM user_apikey WHERE uid = ?", uid);
        } catch (Exception e) {
            return 0;
        }
    }
}
