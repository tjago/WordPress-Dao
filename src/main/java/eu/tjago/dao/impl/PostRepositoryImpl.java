package eu.tjago.dao.impl;

import eu.tjago.dao.PostRepository;
import eu.tjago.entity.Post;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Optional;

/**
 * Created by tjago on 18.07.2016.
 */
public class PostRepositoryImpl implements PostRepository {

    Logger logger = Logger.getLogger(this.getClass());

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("wordpress-dao");

    @Override
    public Long insertPost(Post post) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(post);
            em.getTransaction().commit();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return post.getId();
    }

    @Override
    public Post getPostById(Long postId) {

        EntityManager em = emf.createEntityManager();
        Optional<Post> collectedPost = Optional.empty();
        try {
            em.getTransaction().begin();

            Query query = em.createNamedQuery(Post.GET_POST_BY_ID);
            query.setParameter("postId", postId);

            collectedPost = Optional.of((Post)query.getSingleResult());
            em.getTransaction().commit();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return collectedPost.orElse(null);
    }

    @Override
    public Boolean updatePost(Post post) {
        return null;
    }

    @Override
    public Boolean deletePost(Long postId) {

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Post post = em.find(Post.class, postId);
            em.remove(post);
            em.getTransaction().commit();
            return true;
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            //TODO detect if finally will be called if return is put at end of try block
            em.close();
        }
        return false;
    }
}
