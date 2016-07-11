package eu.tjago;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import eu.tjago.dao.UserRepository;
import eu.tjago.dao.impl.UserRepositoryImpl;
import eu.tjago.entity.User;
import eu.tjago.entity.UserMeta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertTrue;

/**
 * Created by tjago on 2016-04-17.
 */

//For DB DAO Test use H2 DB

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnit4.class)
//@RunWith(WeldJUnit4Runner.class)
public class UserMetaDaoTest {

    @ObjectUnderTest
    private UserRepository userRepository = new UserRepositoryImpl();

    private User jonathanSmith = new User("natan21", "natan@mailinator.com");

    @Before
    public void setUpDB() {
        userRepository.insertUser(jonathanSmith);
        UserMeta firstName = new UserMeta(jonathanSmith.getId(), "FirstName", "Jonathan");
        UserMeta lastName = new UserMeta(jonathanSmith.getId(), "LastName", "Smith");

        userRepository.setUserMeta(firstName);
        userRepository.setUserMeta(lastName);
    }

    @Test
    public void readUserMeta() {
        String firstName = userRepository.getUserMetaByKey("FirstName", jonathanSmith.getId());

        assertTrue(firstName.equals("Jonathan"));
    }

    @After
    public void cleanUpDB() {
        userRepository.removeUserByID(jonathanSmith.getId());
    }
}
