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
        if(post.getAuthor() == null) {
            throw new RuntimeException("Post can't be created with null User");
        }
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
    public Optional<Post> getPostById(Long postId) {

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Query query = em.createNamedQuery(Post.GET_POST_BY_ID);
            query.setParameter("postId", postId);
            Post fetchedPost = (Post)query.getSingleResult();

            em.getTransaction().commit();

            return Optional.ofNullable(fetchedPost);
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return Optional.empty();
    }

    @Override
    public Boolean updatePost(Post post) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(post);
            em.getTransaction().commit();
            return true;
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return false;
    }

    @Override
    public Boolean deletePostById(Long postId) {

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
            em.close();
        }
        return false;
    }
}
