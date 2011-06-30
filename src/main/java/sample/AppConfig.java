package sample;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 6/30/11
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class AppConfig {
    /*@Bean
    public SimpleJdbcTemplate simpleJdbcTemplate() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/twimini?user=purav&password=purav");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("");
        dataSource.setPassword("");
        SimpleJdbcTemplate db = new SimpleJdbcTemplate(dataSource);

        db.update("create table user(uid int primary key)");
        return db;
    }*/
}
