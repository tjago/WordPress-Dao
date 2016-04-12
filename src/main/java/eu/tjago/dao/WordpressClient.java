package eu.tjago.dao;

import eu.tjago.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.Set;

public class WordpressClient {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("wordpress-dao");

    public void insertUser(String username, String email) {

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            //TODO Logic here
            User formUser = new User(username, email);
            formUser.setDisplayName("Don"); //changed in form default value

            entityManager.persist(formUser);

            transaction.commit();

        } catch(Exception e) {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        } finally {
            if(entityManager != null) { entityManager.close(); }
        }
    }


    public Set<User> getAllUsers() {

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();


            transaction.commit();

        } catch(Exception e) {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        } finally {
            if(entityManager != null) { entityManager.close(); }
        }

        return null;
    }

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