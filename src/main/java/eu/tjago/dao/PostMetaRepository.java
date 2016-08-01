package eu.tjago.dao;

import eu.tjago.entity.PostMeta;

import java.util.List;
import java.util.Optional;

/**
 * Created by jagod on 24/07/2016.
 */
public interface PostMetaRepository extends GenericDao {

    Optional<PostMeta> getOneForPost(Long postId, String key);

    Optional<String> getPostMataValue(Long postId, String key);

    Optional<List<PostMeta>> getAllForPost(Long postId);
}
