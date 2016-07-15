package eu.tjago.dao;

import eu.tjago.entity.User;
import eu.tjago.entity.UserMeta;

import java.util.List;

/**
 * Created by tjago on 2016-04-14.
 */

public interface UserRepository {

    void insertUser(String username, String email);

    void insertUser(User user);

    boolean updateUser(User user);

    void removeUserByID(Long userId);

    List<User> getAllUsers();

    User getSingleUser(Long userId);

    List<UserMeta> getAllUserMeta(Long userId);

    String getUserMetaByKey(String key, Long userId);

    void setUserMeta(UserMeta userMeta);
}
