package eu.tjago.dao;

import eu.tjago.entity.PostMeta;
import eu.tjago.entity.UserMeta;

import java.util.List;

/**
 * Created by jagod on 24/07/2016.
 */
public interface PostMetaRepository extends GenericDao {

//    List<PostMeta> getAllPostMeta(Long userId);

    String getPostMetaByKey(String key, Long postId);

//    void setPostMeta(PostMeta postMeta);
}
