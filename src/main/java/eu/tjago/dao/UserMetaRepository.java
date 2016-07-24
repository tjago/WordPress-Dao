package eu.tjago.dao;

import eu.tjago.entity.UserMeta;

import java.util.List;

/**
 * Created by jagod on 24/07/2016.
 */
public interface UserMetaRepository {

    List<UserMeta> getAllUserMeta(Long userId);

    String getUserMetaByKey(String key, Long userId);

    void setUserMeta(UserMeta userMeta);
}
