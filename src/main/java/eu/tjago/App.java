package eu.tjago;

import eu.tjago.dao.UserRepository;
import eu.tjago.dao.impl.UserRepositoryImpl;
import eu.tjago.util.DbUtil;

import java.util.List;
import java.util.Optional;

/**
 * Hello world!
 * Good reference on JPA 2.1
 *
 * http://www.thoughts-on-java.org/jpa-21-overview/
 *
 */
public class App 
{
    UserRepository userRepository = new UserRepositoryImpl();

    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "JPA App starting.." );
        new App().run();
    }

    private void run() {
//        DbUtil dbUtil = new DbUtil();
//        dbUtil.createTables();

        userRepository.insertUser("mrpaullo", "JohnPaullo@gmail.com");

        Optional<List> users = Optional.ofNullable(userRepository.getAllUsers());

        users.ifPresent(u -> u.stream().forEach(System.out::println));
    }
}
