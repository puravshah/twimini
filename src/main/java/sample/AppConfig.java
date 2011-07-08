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
    @Bean
    public SimpleJdbcTemplate simpleJdbcTemplate() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/twimini");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("pajdpggp123");
        SimpleJdbcTemplate db = new SimpleJdbcTemplate(dataSource);

        /*db.update("create table user(" +
                    "uid int primary key auto_increment," +
                    "firstname varchar(20) not null," +
                    "lastname varchar(20)," +
                    "email varchar(30) unique not null," +
                    "password varchar(50) not null," +
                    "timestamp datetime not null )"
                    );

                    db.update("create table post(" +
                    "pid bigint primary key auto_increment, " +
                    "uid int references user(uid) on delete cascade, " +
                    "tweet varchar(100) not null, " +
                    "timestamp datetime not null )"
                    );

                    db.update("create table likes(" +
                    "pid bigint references post(pid) on delete cascade, " +
                    "uid int references user(uid) on delete cascade, " +
                    "timestamp datetime not null, " +
                    "primary key(pid, uid) )"
                    );

                    db.update("create table follow(" +
                    "uid int references user(uid) on delete cascade, " +
                    "following int references user(uid) on delete cascade, " +
                    "start datetime not null, " +
                    "end datetime, " +
                    "primary key(uid, following, start) )"
                    );
          */
        return db;

    }
}

