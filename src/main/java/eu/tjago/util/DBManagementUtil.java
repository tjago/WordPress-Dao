package eu.tjago.util;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.inject.Inject;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBManagementUtil {


    @Inject
    @ConfigProperty(name = "database.user")
    private String user;

    @Inject
    @ConfigProperty(name = "database.password")
    private String password;

    public void createTables() {

        try {

            Reader reader = Resources.getResourceAsReader("schema.sql");

            Class.forName("org.mariadb.jdbc.Driver");

            //dbname doesn't matter, it's overriden in sql script
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wordpress", "root", "forest22");

            ScriptRunner runner = new ScriptRunner(conn);

            runner.runScript(reader);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}