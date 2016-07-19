package eu.tjago.dao;

import eu.tjago.entity.Post;

/**
 * Created by tjago on 17.07.2016.
 */
public interface PostRepository {

    Long insertPost(Post post);

    Post getPostById(Long postId);

    Boolean updatePost(Post post);

    Boolean deletePost(Long postId);
}
