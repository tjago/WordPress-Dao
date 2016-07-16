package eu.tjago.dao.impl;

import eu.tjago.dao.UserRepository;
import eu.tjago.entity.User;
import eu.tjago.entity.UserMeta;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

/**
 * Created by tjago on 2016-04-14.
 */
public class UserRepositoryImpl implements UserRepository {

    Logger logger = Logger.getLogger(this.getClass());

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("wordpress-dao");

    @Override
    public Long insertUser(String username, String email) {
        return insertUser(new User(username, email));
    }

    @Override
    public Long insertUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user.getId();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return true;
    }

    @Override
    public void removeUserByID(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);
            em.remove(user);
            em.getTransaction().commit();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
    }

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

    public void setUserMeta(Long userId, String key, String value) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
//            em.persist(userMeta);
            em.getTransaction().commit();

        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            e.printStackTrace();
        } finally {
            em.close();
        }
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

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery(User.GET_ALL_USERS);
            users = query.getResultList();
            em.getTransaction().commit();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return users;
    }

    @Override
    public User getSingleUser(Long userId) {
        User user = null;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query namedQuery = em.createNamedQuery(User.GET_USER_BY_ID);
            namedQuery.setParameter("userId", userId);
            user = (User)namedQuery.getSingleResult();
            em.getTransaction().commit();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return user;
    }

    public List<User> getUsersLike(String pattern) {
        List<User> users = null;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query namedQuery = em.createNamedQuery(User.GET_USERS_LIKE);
            namedQuery.setParameter("pattern", "%" + pattern + "%");
            users = namedQuery.getResultList();
            em.getTransaction().commit();
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return users;
    }
}
