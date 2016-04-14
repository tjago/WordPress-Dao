package eu.tjago.dao;

import eu.tjago.entity.User;
import eu.tjago.entity.UserMeta;

import java.util.List;

/**
 * Created by Tomasz on 2016-04-14.
 */

public interface UserRepository {

    void insertUser(String username, String email);

    void insertUser(User user);

    List<User> getAllUsers();

    User getSingleUser(Long userId);

    List<UserMeta> getUserMeta(Long userId);

    void setUserMeta(Long userId, String key, String value);
}
