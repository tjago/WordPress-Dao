package eu.tjago.dao;

import java.util.Optional;

/**
 * Created by jagod on 24/07/2016.
 */
public interface PostMetaRepository extends GenericDao {

    Optional getOneForPost(Long postId, String key);

    Optional getAllForPost(Long postId);
}
