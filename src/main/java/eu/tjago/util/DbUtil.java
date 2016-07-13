package eu.tjago.util;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;

@ApplicationScoped
public class DbUtil {


    @Inject
    @ConfigProperty(name = "database.user")
    private String user;

    @Inject
    @ConfigProperty(name = "database.password")
    private String password;

    @Inject
    @ConfigProperty(name = "database.database")
    private String database;

    @Inject
    @ConfigProperty(name = "database.port")
    private String port;

    @Inject
    @ConfigProperty(name = "database.host")
    private String host;

    public void createTables() {

        try {

            Reader reader = Resources.getResourceAsReader("schema.sql");

            Class.forName("org.mariadb.jdbc.Driver");

            String connAddress = "jdbc:mysql://" + host + ":" + port + "/" + database;

            Connection conn = DriverManager.getConnection(connAddress, user, password);

            ScriptRunner runner = new ScriptRunner(conn);

            runner.runScript(reader);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void configureDbUtil(String host, String user, String password, String database, String port) {
        this.database = database;
        this.port = port;
        this.password = password;
        this.user = user;
        this.host = host;
    }
}