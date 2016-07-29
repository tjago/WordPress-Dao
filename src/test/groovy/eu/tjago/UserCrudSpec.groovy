package eu.tjago;

import eu.tjago.dao.UserRepository
import eu.tjago.dao.impl.UserRepositoryImpl
import eu.tjago.entity.User
import eu.tjago.util.PasswordUtil
import spock.lang.Specification

import java.time.LocalDateTime

/**
 *  Created by tjago on 12.07.2016.
 */
class UserCrudSpec extends Specification {

    UserRepository userRepository;

    def setup() {
        userRepository = new UserRepositoryImpl();
    }

    def "add user, update user and delete user"() {

        setup: "add new user with password"
            User janKowalski = new User("janek123", "jan@kowalski.com", "ToughPass123");
            userRepository.insertUser(janKowalski);

        when: "pseudo user Nicename is Zloty"
            janKowalski.setNicename("Zloty");

        and: "update user field"
            userRepository.updateUser(janKowalski);

        then: "fetch user and update user is success"
            userRepository.getUserById(janKowalski.getId()).getNicename().equals("Zloty")

        cleanup: "Delete user"
            userRepository.removeUserByID(janKowalski.getId());
    }

    def "verify new user has date created"() {

        setup: "add new user with password"
            User janKowalski = new User("janek123", "jan@kowalski.com", "ToughPass123");

        when: "inserted new user"
            userRepository.insertUser(janKowalski);

        and: "get time registered"
            LocalDateTime registrationTime = userRepository.getUserById(janKowalski.getId()).getRegistered()

        and: "get today's time"
            LocalDateTime now = LocalDateTime.now();

        then: "compare if it equals with today date, don't run on 0:00 hour"
            registrationTime.getYear() == now.getYear();
            registrationTime.getMonth() == now.getMonth();
            registrationTime.getDayOfMonth() == now.getDayOfMonth();
            registrationTime.getHour() <= now.getHour();

        cleanup: "Delete user"
            userRepository.removeUserByID(janKowalski.getId());
    }

    def "verify hashing user password works"() {

        setup: "add new user with password"
            String plainTextPassword = "ToughPass123";
            User janKowalski = new User("janek123", "jan@kowalski.com", plainTextPassword);
            userRepository.insertUser(janKowalski);

        when: "Hashed password is obtained"
            String hashedPassword = userRepository.getUserById(janKowalski.getId()).getPassword();

        then: "verify plain password is not same as hashed one"
            hashedPassword != plainTextPassword;

        and: "verify hashed password is quite long"
            hashedPassword.length() > 40;

        and: "finally verify hashed password against plain password"
            PasswordUtil.verifyHashPassword(plainTextPassword, hashedPassword)

        cleanup: "Delete Jan Kowalski"
            userRepository.removeUserByID(janKowalski.getId());
    }

    def "verify user can't be added with same email twice"() {
        given: "two users with same email"
            User joshua = new User("Joshua Wilkinsonn", "josh@wilkinsonn.com", "razor4ever")
            User brad = new User("Brad Orwell", "josh@wilkinsonn.com", "farm564")

        when:"users are inserted in repository"
            userRepository.insertUser(joshua);
            userRepository.insertUser(brad);

        then:"joshua is persisted to DB"
            joshua.getId() != null

        and:"brad is not persisted"
            brad.getId() == null;

        cleanup:"delete users"
            if(joshua.getId() != null) {
                userRepository.removeUserByID(joshua.getId())
            }
            if(brad.getId() != null) {
                userRepository.removeUserByID(brad.getId())
            }

    }
}
