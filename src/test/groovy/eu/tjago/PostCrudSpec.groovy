package eu.tjago

import eu.tjago.dao.PostRepository
import eu.tjago.dao.UserRepository
import eu.tjago.dao.impl.PostRepositoryImpl
import eu.tjago.dao.impl.UserRepositoryImpl
import eu.tjago.entity.Post
import eu.tjago.entity.User
import spock.lang.Specification
/**
 * Created by tjago on 18.07.2016.
 */
class PostCrudSpec extends Specification {

    PostRepository postRepository;
    UserRepository userRepository;

    def setup() {
        postRepository = new PostRepositoryImpl();
        userRepository = new UserRepositoryImpl();
    }

    def "insert post"() {
        given:"new post was created"
            Long userId = userRepository.insertUser(new User("niceGuy", "russel@hollywood.com", "sourceofwater"));
            Post todayNews = new Post("People has landed on Mars", "Breaking news!", userRepository.getSingleUser(userId));

        when:"insert post in DB"
            Long postId = postRepository.insertPost(todayNews);

        then:"post content from DB matches inserted String"
            postRepository.getPostById(postId).getContent().equals("People has landed on Mars");

        cleanup:"remove post"
            postRepository.deletePostById(postId);
            userRepository.removeUserByID(userId);
    }

}
