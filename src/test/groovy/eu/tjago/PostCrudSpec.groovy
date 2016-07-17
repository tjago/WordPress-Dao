package eu.tjago

import eu.tjago.dao.PostRepository
import eu.tjago.dao.impl.PostRepositoryImpl
import eu.tjago.entity.Post
import eu.tjago.entity.User
import spock.lang.Specification

/**
 * Created by tjago on 18.07.2016.
 */
class PostCrudSpec extends Specification {

    PostRepository postRepository;

    def setup() {
        postRepository = new PostRepositoryImpl();
    }

    def "insert post"() {
        given:"new post was created"
        User user = getUser();
        Post todayNews = new Post("People has landed on Mars", "Breaking news!", user);

        when:"insert post in DB"
        Long postId = postRepository.insertPost(todayNews);

        then:"post content from DB matches inserted String"
        postRepository.getPost(postId).getContent().equals("People has landed on Mars");

        cleanup:"remove post"
        postRepository.deletePost(postId);
    }

    User getUser() {
        null
    }
}
