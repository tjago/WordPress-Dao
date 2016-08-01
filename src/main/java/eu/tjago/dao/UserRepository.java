package eu.tjago.dao;

import eu.tjago.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by tjago on 2016-04-14.
 */

public interface UserRepository extends GenericDao {

    void removeUserByID(Long userId);

    List<User> getAllUsers();

    Optional<User> getUserByEmail(String email);

    Optional<List<User>> getUsersLike(String pattern);
}
