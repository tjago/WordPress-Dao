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
        if (!user.getEmail().isEmpty() &&
                !getUserByEmail(user.getEmail()).isPresent()) {

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
    public User getUserById(Long userId) {
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

    @Override
    public Optional<User> getUserByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Query namedQuery = em.createNamedQuery(User.GET_USER_BY_EMAIL);
            namedQuery.setParameter("email", email);
            User user = (User)namedQuery.getSingleResult();

            em.getTransaction().commit();

            return Optional.ofNullable(user);
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.info(e.getMessage());
        } finally {
            em.close();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<User>> getUsersLike(String pattern) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Query namedQuery = em.createNamedQuery(User.GET_USERS_LIKE);
            namedQuery.setParameter("pattern", "%" + pattern + "%");
            List<User> users = namedQuery.getResultList();

            em.getTransaction().commit();

            return Optional.ofNullable(users);
        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return Optional.empty();
    }
}
