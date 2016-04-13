package eu.tjago;

import eu.tjago.dao.WordpressClient;
import eu.tjago.entity.User;

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
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "JPA App starting.." );
        new App().run();
    }

    private void run() {
        WordpressClient wpClient = new WordpressClient();

//        wpClient.createTables();
//        wpClient.insertUser("mrpaullo", "JohnPaullo@gmail.com");


        Optional<List> users = Optional.ofNullable(wpClient.getAllUsers());

        users.ifPresent(u -> u.stream().forEach(System.out::println));
    }
}
