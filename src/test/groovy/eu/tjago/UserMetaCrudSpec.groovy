package eu.tjago

import eu.tjago.dao.UserMetaRepository
import eu.tjago.dao.UserRepository
import eu.tjago.dao.impl.UserMetaRepositoryImpl
import eu.tjago.dao.impl.UserRepositoryImpl
import eu.tjago.entity.User
import eu.tjago.entity.UserMeta
import spock.lang.Specification

/**
 * Created by jagod on 24/07/2016.
 */
class UserMetaCrudSpec extends Specification{

    UserRepository userRepository;
    UserMetaRepository userMetaRepository;

    def setup() {
        userRepository = new UserRepositoryImpl();
        userMetaRepository = new UserMetaRepositoryImpl();
    }


    def "can create UserMeta for a user"() {

        setup: "add new user"
            User jonathanSmith = new User("natan21", "natan@mailinator.com");
            userRepository.insertUser(jonathanSmith);

        when: "UserMeta is added to profile"
            userMetaRepository.setUserMeta(new UserMeta(jonathanSmith.getId(), "FirstName", "Jonathan"));
            userMetaRepository.setUserMeta(new UserMeta(jonathanSmith.getId(), "LastName", "Smith"));

        then: "obtain user name by passing id and key"
            String userName = userMetaRepository.getUserMetaByKey("FirstName", jonathanSmith.getId())

        expect:
            userName == "Jonathan"

        cleanup:
            userRepository.removeUserByID(jonathanSmith.getId())

    }
}
