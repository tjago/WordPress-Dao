package eu.tjago.dao;

import eu.tjago.entity.Post;

import java.util.Optional;

/**
 * Created by tjago on 17.07.2016.
 */
public interface PostRepository {

    Long insertPost(Post post);

    Optional<Post> getPostById(Long postId);

    Boolean updatePost(Post post);

    Boolean deletePostById(Long postId);
}
