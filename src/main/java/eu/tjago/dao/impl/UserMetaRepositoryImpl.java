package eu.tjago.dao.impl;

import eu.tjago.dao.UserMetaRepository;
import eu.tjago.entity.UserMeta;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * Created by jagod on 24/07/2016.
 */
public class UserMetaRepositoryImpl implements UserMetaRepository {

    Logger logger = Logger.getLogger(this.getClass());

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("wordpress-dao");

    @Override
    public List<UserMeta> getAllUserMeta(Long userId) {
        List<UserMeta> usermeta = null;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery(UserMeta.GET_ALL_USERMETA);
            query.setParameter("userId", userId);
            usermeta = query.getResultList();

            //print all usermeta to Console
            //usermeta.stream().forEach(UserMeta::toString);
            em.getTransaction().commit();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return usermeta;
    }

    @Override
    public String getUserMetaByKey(String key, Long userId) {

//        UserMeta userMeta = null;
        Optional<UserMeta> userMeta = Optional.empty();

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Query query = em.createNamedQuery(UserMeta.GET_USERMETA_BY_KEY);
            query.setParameter("userId", userId);
            query.setParameter("key", key);

            userMeta = Optional.of((UserMeta)query.getSingleResult());
            //print all usermeta to Console
            System.out.format("\nUsermeta for user %s and key[%s] = %s .", userId.toString(), key, userMeta.get().getValue());
            em.getTransaction().commit();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return userMeta.orElse(null).getValue();
    }

    @Override
    public void setUserMeta(UserMeta userMeta) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(userMeta);
            em.getTransaction().commit();

        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
    }

    public void setUserMeta(Long userId, String key, String value) {
        setUserMeta(new UserMeta(userId, key, value));
    }
}
