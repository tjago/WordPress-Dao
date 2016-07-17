package eu.tjago.dao.impl;

import eu.tjago.dao.PostRepository;
import eu.tjago.entity.Post;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
            return post.getId();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public Post getPost(Long postId) {
        return null;
    }

    @Override
    public Boolean updatePost(Post post) {
        return null;
    }

    @Override
    public Boolean deletePost(Long postId) {
        return null;
    }
}
