package eu.tjago.util;

import eu.tjago.entity.User;
import eu.tjago.entity.UserMeta;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.persistence.*;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class DBManagementUtil {

    public void createTables() {

        try {

            Reader reader = Resources.getResourceAsReader("schema.sql");

            Class.forName("org.mariadb.jdbc.Driver");

            //dbname doesn't matter, it's overriden in sql script
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3310/wordpress", "root", "pass123");

            ScriptRunner runner = new ScriptRunner(conn);

            runner.runScript(reader);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}