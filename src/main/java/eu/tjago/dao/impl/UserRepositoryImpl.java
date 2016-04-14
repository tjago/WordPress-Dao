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

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        User user = new User(username, email);

        try {
            transaction.begin();
//            formUser.setDisplayName("Don"); //changed in form default value

            entityManager.persist(user);
            transaction.commit();

        } catch(Exception e) {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void insertUser(User user) {

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();

        } catch(Exception e) {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<UserMeta> getUserMeta(Long userId) {

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Query query = entityManager.createQuery("select um from UserMeta as um where um.userId = :userId"); //TODO create Named Query
            query.setParameter("userId", userId);

            List<UserMeta> usermeta = query.getResultList();

            //print all users to Console
            usermeta.stream().forEach(UserMeta::toString);

            transaction.commit();

        } catch(Exception e) {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return null;
    }

    @Override
    public void setUserMeta(Long userId, String key, String value) {

    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = null;

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Query query = entityManager.createNamedQuery(User.GET_ALL_USERS);
            users = query.getResultList();

            transaction.commit();

        } catch(Exception e) {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return users;
    }

    @Override
    public User getSingleUser(Long userId) {
        return  null;
    }

    public List<User> getUsersLike(String pattern) {
        List<User> users = null;

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Query namedQuery = entityManager.createNamedQuery(User.GET_USERS_LIKE);
            namedQuery.setParameter("pattern", "%" + pattern + "%");
            users = namedQuery.getResultList();

            transaction.commit();

        } catch(Exception e) {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return users;

    }
}
