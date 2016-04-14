package eu.tjago.dao.impl;

import eu.tjago.dao.UserRepository;
import eu.tjago.entity.User;
import eu.tjago.entity.UserMeta;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Tomasz on 2016-04-14.
 */
public class UserRepositoryImpl implements UserRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("wordpress-dao");

    @Override
    public void insertUser(String username, String email) {

        EntityManager em = emf.createEntityManager();

        User user = new User(username, email);

        try {
            em.getTransaction().begin();
//            formUser.setDisplayName("Don"); //changed in form default value

            em.persist(user);
            em.getTransaction().commit();

        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void insertUser(User user) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            e.printStackTrace();
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
            usermeta.stream().forEach(UserMeta::toString);

            em.getTransaction().commit();

        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return usermeta;
    }

    @Override
    public String getUserMetaByKey(String key, Long userId) {

        String usermetaValue = "";

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Query query = em.createNamedQuery(UserMeta.GET_USERMETA_BY_KEY);
            query.setParameter("userId", userId);
            query.setParameter("key", key);

            usermetaValue = (String) query.getSingleResult();

            //print all usermeta to Console
            System.out.format("\nUsermeta for user %s and key[%s] = %s .", userId.toString(), key, usermetaValue);

            em.getTransaction().commit();

        } catch(Exception e) {
            if(em.getTransaction() != null) { em.getTransaction().rollback(); }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return usermetaValue;
    }

    public void setUserMeta(Long userId, String key, String value) {
        //TODO remove or implement when necessary
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
            e.printStackTrace();
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
        return  null;
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
            e.printStackTrace();
        } finally {
            em.close();
        }

        return users;

    }
}
