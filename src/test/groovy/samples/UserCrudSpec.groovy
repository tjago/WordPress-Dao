package samples

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest
import eu.tjago.dao.UserRepository
import eu.tjago.dao.impl.UserRepositoryImpl
import eu.tjago.entity.User
import eu.tjago.entity.UserMeta
import spock.lang.Specification

/**
*  Created by tjago on 12.07.2016.
*/
class UserCrudSpec extends Specification {

    @ObjectUnderTest
    UserRepository userRepository = new UserRepositoryImpl();

    def setup() {

    }

    def "can read UserMeta"() {

        setup: "add new user"
            User jonathanSmith = new User("natan21", "natan@mailinator.com");
            userRepository.insertUser(jonathanSmith);

        when: "UserMeta is added to profile"
            userRepository.setUserMeta(new UserMeta(jonathanSmith.getId(), "FirstName", "Jonathan"));
            userRepository.setUserMeta(new UserMeta(jonathanSmith.getId(), "LastName", "Smith"));

        then: "obtain user name by passing id and key"
            String userName = userRepository.getUserMetaByKey("FirstName", jonathanSmith.getId())

        expect:
            userName.equals("Jonathan")

        cleanup:
            userRepository.removeUserByID(jonathanSmith.getId())

    }
}
