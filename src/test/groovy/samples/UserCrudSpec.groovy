package samples

import eu.tjago.dao.UserRepository
import eu.tjago.dao.impl.UserRepositoryImpl
import eu.tjago.entity.User
import eu.tjago.entity.UserMeta
import eu.tjago.util.EpuapSystem
import spock.lang.Specification
/**
*  Created by tjago on 12.07.2016.
*/
class UserCrudSpec extends Specification {

    UserRepository userRepository;
    EpuapSystem epuapSystem;

    def setup() {
        userRepository = new UserRepositoryImpl();

//        epuapSystem = Stub(EpuapSystem){
//            getCitizenDetails("1234") >> "Jan Kowalski"
//        }
    }

    def "add user, update user and delete user"() {

        setup: "add new user with password"
            User janKowalski = new User("janek123", "jan@kowalski.com", "ToughPass123");
            userRepository.insertUser(janKowalski);

        when:"fetch user and update user is success"
            janKowalski.setNicename("Zloty");
        and:
            userRepository.updateUser(janKowalski);

        then:"pseudo user Nicename is Zloty"
            userRepository.getSingleUser(janKowalski.getId()).getNicename().equals("Zloty")

        cleanup:"Delete Jan Kowalski"
            userRepository.removeUserByID(janKowalski.getId());
    }

    def "can read UserMeta for specified user"() {

        setup: "add new user"
            User jonathanSmith = new User("natan21", "natan@mailinator.com");
            userRepository.insertUser(jonathanSmith);

        when: "UserMeta is added to profile"
            userRepository.setUserMeta(new UserMeta(jonathanSmith.getId(), "FirstName", "Jonathan"));
            userRepository.setUserMeta(new UserMeta(jonathanSmith.getId(), "LastName", "Smith"));

        then: "obtain user name by passing id and key"
            String userName = userRepository.getUserMetaByKey("FirstName", jonathanSmith.getId())
            String tmpName = epuapSystem.getCitizenDetails("1234");

        expect:
            userName.equals("Jonathan")
            tmpName.equals("Jan Kowalski")

        cleanup:
            userRepository.removeUserByID(jonathanSmith.getId())

    }

    def "verify new user has date created"() {
        //TODO
    }
}
