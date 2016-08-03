package eu.tjago

import eu.tjago.dao.PostMetaRepository
import eu.tjago.dao.PostRepository
import eu.tjago.dao.UserRepository
import eu.tjago.dao.impl.PostMetaRepositoryImpl
import eu.tjago.dao.impl.PostRepositoryImpl
import eu.tjago.dao.impl.UserRepositoryImpl
import eu.tjago.entity.Post
import eu.tjago.entity.PostMeta
import eu.tjago.entity.User
import spock.lang.Specification

/**
 * Created by jagod on 31/07/2016.
 */
class PostMetaCrudSpec extends Specification {

    UserRepository userRepository;
    PostRepository postRepository;
    PostMetaRepository postMetaRepository;
    User user;
    Post news;

    def setupSpec() {
    }

    def setup() {
        userRepository = new UserRepositoryImpl();
        postRepository = new PostRepositoryImpl();
        postMetaRepository = new PostMetaRepositoryImpl();

        user = new User("karateMastaa", "chuck68", "chuck@texas.us");
        userRepository.insertUser(user);
        news = new Post("Hot today! Wear hats and drink water.", "Weather alert", user);
        postRepository.insertPost(news);
    }

    //TODO use optional !
    def "insert new PostMeta to DB"() {

        given:"new Postmeta"
            def newsTags = new PostMeta(news.getId(), "Tags", "weather, alert")

        when:"persist postmeta"
            postMetaRepository.create(newsTags);

        and:"get postmeta from dao"
            PostMeta fetchedTags = postMetaRepository.read(newsTags.getId()).orElse(new PostMeta()) as PostMeta;

        then: "verify fetched data matched input"
            fetchedTags.getValue() == newsTags.getValue();

        cleanup:"delete PostMeta"
            postMetaRepository.delete(newsTags);
    }

    def "modify PostMeta"() {

        given:"new PostMeta"
            def newsTags = new PostMeta(news.getId(), "Tags", "weather, alert")

        when:"persist PostMeta"
            postMetaRepository.create(newsTags);

        and:"get PostMeta from dao and modify"
            PostMeta fetchedTags = postMetaRepository.read(newsTags.getId()).get() as PostMeta;
            fetchedTags.setValue("money, charts")

        and: "modify and merge PostMeta"
            PostMeta fetchedTagsModified = postMetaRepository.read(fetchedTags.getId()).get() as PostMeta;

        then: "verify fetched data matched input"
            fetchedTagsModified.getValue() == "money, charts";

        cleanup:"delete PostMeta"
            postMetaRepository.delete(newsTags);
    }



    def "get list of PostMeta"() {

        given:"new multiple Postmeta"
            def newsTags = new PostMeta(news.getId(), "Tags", "weather, alert")
            def sponsorLink = new PostMeta(news.getId(), "sponsorLink", "http://bbc.co.uk")
            def relatedArticles = new PostMeta(news.getId(), "relatedArticles", "/last-summer-heat;/dried-rivers")

        when:"saved postmeta"
            postMetaRepository.create(newsTags)
            postMetaRepository.create(sponsorLink)
            postMetaRepository.create(relatedArticles)

        and:"get all Meta for Post"
            def listOfPostMeta = postMetaRepository.getAllForPost(news.getId())

        then:"check postmeta List size"
                listOfPostMeta.get().size()  == 3

        and:"list contains objects"
            listOfPostMeta.get().contains(newsTags)
            listOfPostMeta.get().contains(sponsorLink)
            listOfPostMeta.get().contains(relatedArticles)

        cleanup:"delete all PostMeta"
            postMetaRepository.delete(newsTags)
            postMetaRepository.delete(sponsorLink)
            postMetaRepository.delete(relatedArticles)
    }

    def "get PostMeta by key"() {

        given:"new PostMeta"
            def metaKey = "Tags"
            def sponsorLink = new PostMeta(news.getId(), "sponsorLink", "http://bbc.co.uk")
            def newsTags = new PostMeta(news.getId(), metaKey, "weather, alert")

        when:"persist PostMeta"
            postMetaRepository.create(newsTags);

        and:"get PostMeta from dao"
            PostMeta fetchedTags = postMetaRepository.getOneForPost(news.getId(), metaKey).orElse(new PostMeta()) as PostMeta;

        then: "verify fetched data matched input"
            fetchedTags.getValue() == newsTags.getValue();

        cleanup:"delete PostMeta"
            postMetaRepository.delete(sponsorLink)
            postMetaRepository.delete(newsTags);
    }

    def cleanup() {
        if (news.getId() != null) {
            postRepository.deletePostById(news.getId());
        }
        if(user.getId() != null) {
            userRepository.removeUserByID(user.getId());
        }
    }

}
