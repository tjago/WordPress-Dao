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
    User newUser;
    String plainTextPassword = "ToughPass123";

    def setup() {
        userRepository = new UserRepositoryImpl();
        newUser = new User("janek123", "jan@kowalski.com", plainTextPassword);
    }

    def cleanup() {
        userRepository.delete(newUser);
    }

    def "add user, update user and delete user"() {

        setup: "persist user"
            userRepository.create(newUser);

        when: "pseudo user Nicename is Zloty"
            newUser.setNicename("Zloty");

        and: "update user field"
            userRepository.update(newUser);

        then: "fetch user and and verify updated field"
            def fetchedUser = (Optional<User>)userRepository.read(newUser.getId())
            if(fetchedUser.isPresent()) {
                fetchedUser.get().getNicename() == "Zloty"
            }

        cleanup:"delete user from DB"
            userRepository.delete(newUser)
    }

    def "verify new user has date created"() {

        setup: "create date object"
            LocalDateTime registrationTime = null;

        when: "inserted new user"
            userRepository.create(newUser);

        and: "get time registered"
            def fetchedUser = (Optional<User>)userRepository.read(newUser.getId())
            if(fetchedUser.isPresent()) {
                registrationTime = fetchedUser.get().getRegistered()
            }

        and: "get today's time"
            LocalDateTime now = LocalDateTime.now();

        then: "compare if it equals with today date, don't run on 0:00 hour"
            registrationTime != null;
            registrationTime.getYear() == now.getYear();
            registrationTime.getMonth() == now.getMonth();
            registrationTime.getDayOfMonth() == now.getDayOfMonth();
            registrationTime.getHour() <= now.getHour();

        cleanup:"delete user from DB"
            userRepository.delete(newUser)
    }

    def "verify hashing user password works"() {

        setup: "add new user with password"
            userRepository.create(newUser);

        when: "Hashed password is obtained"
            def fetchedUser = (Optional<User>)userRepository.read(newUser.getId())

        then: "verify plain password is not same as hashed one"
            String hashedPassword = fetchedUser.get().getPassword()
            hashedPassword != plainTextPassword;

        and: "verify hashed password is quite long"
            hashedPassword.length() > 40;

        and: "finally verify hashed password against plain password"
            PasswordUtil.verifyHashPassword(plainTextPassword, hashedPassword)
    }

    def "verify user can't be added with same email twice"() {
        given: "two users with same email"
            User joshua = new User("Joshua Wilkinsonn", "josh@wilkinsonn.com", "razor4ever")
            User brad = new User("Brad Orwell", "josh@wilkinsonn.com", "farm564")

        when:"users are inserted in repository"
            userRepository.create(joshua);
            try {
                userRepository.create(brad);
            }
        catch (Exception e) {}

        then:"joshua is persisted to DB"
            joshua.getId() != null

        and:"brad is not persisted"
            brad.getId() == null;

        cleanup:"delete users"
            if(joshua.getId() != null) {
                userRepository.delete(joshua)
            }
            if(brad.getId() != null) {
                userRepository.delete(brad)
            }
    }

    def "verify all users can be fetched"() {

        given: "two users with same email"
            User joshua = new User("Joshua Wilkinson", "josh@wilkinsonn.com", "razor4ever")
            User brad = new User("Brad Orwell", "josh@wilkinsonn.com", "farm564")
            User zack = new User("Zacknafein", "zack@delves.com", "ER9GB3")

        when: "users are persisted"
            userRepository.create(joshua)
            userRepository.create(brad)
            userRepository.create(zack)

        and: "All users are fetched"
            List<User> allUsers = userRepository.getAllUsers();

        then: "verify"
            allUsers.contains(joshua)
            allUsers.contains(brad)
            allUsers.contains(zack)

        cleanup: "delete users"
            userRepository.delete(joshua)
            userRepository.delete(brad)
            userRepository.delete(zack)
    }
}
