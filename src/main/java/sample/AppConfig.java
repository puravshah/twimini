package sample;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        dataSource.setUrl("jdbc:mysql:mem:twimini");
        dataSource.setDriverClassName("com.mysql.jdbcDriver");
        SimpleJdbcTemplate db = new SimpleJdbcTemplate(dataSource);

        db.update("create table user(\n" +
                "    \tuid int primary key auto_increment,\n" +
                "\tfirstname varchar(20) not null,\n" +
                "\tlastname varchar(20),\n" +
                "\temailid varchar(30) unique not null,\n" +
                "\tpassword varchar(50) not null,\n" +
                "\ttimestamp datetime not null );"

        );

        return db;
    }
}
