package eu.tjago;

import eu.tjago.dao.UserRepository;
import eu.tjago.dao.impl.UserRepositoryImpl;
import eu.tjago.entity.User;
import eu.tjago.entity.UserMeta;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

/**
 * Created by tjago on 2016-04-17.
 */

//For DB DAO Test use H2 DB

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnit4.class)
public class DaoTest {

    private UserRepository userRepository = new UserRepositoryImpl();
    private User jonathanSmith = new User("natan21", "natan@mailinator.com");

    @Before
    public void setUpDB() {
        userRepository.insertUser(jonathanSmith);
    }

    @Test
    public void setUserMeta() {

        UserMeta firstName = new UserMeta(jonathanSmith.getId(), "FirstName", "Jonathan");
        UserMeta lastName = new UserMeta(jonathanSmith.getId(), "LastName", "Smith");

        userRepository.setUserMeta(firstName);
        userRepository.setUserMeta(lastName);
    }

    @Test
    public void readUserMeta() {
        String firstName = userRepository.getUserMetaByKey("FirstName", jonathanSmith.getId());

        assertEquals(firstName, "Jonathan");
    }

    @After
    public void cleanUpDB() {
        userRepository.removeUserByID(jonathanSmith.getId());
    }
}
