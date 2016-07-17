package eu.tjago;

import eu.tjago.dao.UserRepository
import eu.tjago.dao.impl.UserRepositoryImpl
import eu.tjago.entity.User
import eu.tjago.entity.UserMeta
import eu.tjago.util.EpuapSystem
import eu.tjago.util.PasswordUtil
import spock.lang.Specification

import java.time.LocalDateTime

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

        when:"pseudo user Nicename is Zloty"
            janKowalski.setNicename("Zloty");

        and:"update user field"
            userRepository.updateUser(janKowalski);

        then:"fetch user and update user is success"
            userRepository.getSingleUser(janKowalski.getId()).getNicename().equals("Zloty")

        cleanup:"Delete user"
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

        setup: "add new user with password"
        User janKowalski = new User("janek123", "jan@kowalski.com", "ToughPass123");

        when:"inserted new user"
        userRepository.insertUser(janKowalski);

        and:"get time registered"
        LocalDateTime registrationTime = userRepository.getSingleUser(janKowalski.getId()).getRegistered()

        and:"get today's time"
        LocalDateTime now = LocalDateTime.now();

        then:"compare if it equals with today date, don't run on 0:00 hour"
        registrationTime.getYear() == now.getYear();
        registrationTime.getMonth() == now.getMonth();
        registrationTime.getDayOfMonth() == now.getDayOfMonth();
        registrationTime.getHour() <= now.getHour();

        cleanup:"Delete user"
        userRepository.removeUserByID(janKowalski.getId());
    }

    def "verify hashing user password works"() {

        setup: "add new user with password"
        String plainTextPassword = "ToughPass123";
        User janKowalski = new User("janek123", "jan@kowalski.com", plainTextPassword);
        userRepository.insertUser(janKowalski);

        when:"Hashed password is obtained"
        String hashedPassword = userRepository.getSingleUser(janKowalski.getId()).getPassword();

        then:"verify plain password is not same as hashed one"
        !hashedPassword.equals(plainTextPassword);

        and:"verify hashed password is quite long"
        hashedPassword.length() > 40;

        and:"finally verify hashed password against plain password"
        PasswordUtil.verifyHashPassword(plainTextPassword, hashedPassword)

        cleanup:"Delete Jan Kowalski"
        userRepository.removeUserByID(janKowalski.getId());
    }
}
