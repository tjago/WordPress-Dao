package eu.tjago.dao;

import eu.tjago.entity.User;
import eu.tjago.entity.UserMeta;

import java.util.List;
import java.util.Optional;

/**
 * Created by tjago on 2016-04-14.
 */

public interface UserRepository {

    Long insertUser(String username, String email);

    Long insertUser(User user);

    boolean updateUser(User user);

    void removeUserByID(Long userId);

    List<User> getAllUsers();

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByEmail(String email);

    Optional<List<User>> getUsersLike(String pattern);
}
