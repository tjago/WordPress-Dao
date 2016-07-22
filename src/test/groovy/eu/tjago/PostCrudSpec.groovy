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
        given: "new post was created"
            User russel = new User("niceGuy", "russel@hollywood.com", "sourceofwater");
            Long userId = userRepository.insertUser(russel);
            Post todayNews = new Post("People has landed on Mars", "Breaking news!", russel);

        when: "insert post in DB"
            Long postId = postRepository.insertPost(todayNews);

        then: "post content from DB matches inserted String"
            postRepository.getPostById(postId).getContent() == "People has landed on Mars";

        cleanup: "remove post"
            postRepository.deletePostById(postId);
            userRepository.removeUserByID(userId);
    }

    def "verify update of the post"() {
        given:
            User russel = new User("niceGuy", "russel@hollywood.com", "sourceofwater");
            Long userId = userRepository.insertUser(russel);
            Post todayNews = new Post("People has landed on Mars", "Breaking news!", russel);
            postRepository.insertPost(todayNews)
        when:
            todayNews.setContent("People are coming back from Mars");
            postRepository.updatePost(todayNews);
            Post fetchedNews = postRepository.getPostById(todayNews.getId())

        then:
            fetchedNews.getContent() == "People are coming back from Mars"

        cleanup:
            postRepository.deletePostById(todayNews.getId());
            userRepository.removeUserByID(userId);

    }

}
