package eu.tjago.dao.impl;

import eu.tjago.dao.UserRepository;
import eu.tjago.entity.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * Created by tjago on 2016-04-14.
 */
public class UserRepositoryImpl extends GenericDaoImpl implements UserRepository {

    Logger logger = Logger.getLogger(this.getClass());

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("wordpress-dao");

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Object create(Object o) {
        try {
            return insertUser((User)o);
        } catch (Exception e) {
            logger.warn(e);
        }
        return null;
    }

    /**
     * Create user with email Duplication verification
     * @param user
     * @return
     * @throws Exception
     */
    public User insertUser(User user) throws Exception {
        if (!user.getEmail().isEmpty() &&
                !getUserByEmail(user.getEmail()).isPresent()) {

            try {
                entityManager.getTransaction().begin();
                entityManager.persist(user);
                entityManager.getTransaction().commit();
                return user;
            } catch(Exception e) {
                if(entityManager.getTransaction() != null) { entityManager.getTransaction().rollback(); }
                logger.error(e.getMessage());
            }
        } else {
            throw new Exception("Trying to insert an empty user or with same email");
        }
        return null;
    }

    @Override
    public void removeUserByID(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = this.entityManager.find(User.class, userId);
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        } catch(Exception e) {
            if(entityManager.getTransaction() != null) { entityManager.getTransaction().rollback(); }
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            this.entityManager.getTransaction().begin();
            Query query = this.entityManager.createNamedQuery(User.GET_ALL_USERS);
            users = query.getResultList();
            this.entityManager.getTransaction().commit();
        } catch(Exception e) {
            if(this.entityManager.getTransaction() != null) { this.entityManager.getTransaction().rollback(); }
            e.printStackTrace();
        }
        return users;
    }

    @Deprecated
    public Optional<User> getUserById(Long userId) {
        try {
            this.entityManager.getTransaction().begin();

            Query namedQuery = this.entityManager.createNamedQuery(User.GET_USER_BY_ID);
            namedQuery.setParameter("userId", userId);
            User user = (User)namedQuery.getSingleResult();

            this.entityManager.getTransaction().commit();

            return Optional.ofNullable(user);
        } catch(Exception e) {
            if(this.entityManager.getTransaction() != null) { this.entityManager.getTransaction().rollback(); }
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try {
            this.entityManager.getTransaction().begin();

            Query namedQuery = this.entityManager.createNamedQuery(User.GET_USER_BY_EMAIL);
            namedQuery.setParameter("email", email);
            User user = (User)namedQuery.getSingleResult();

            this.entityManager.getTransaction().commit();

            return Optional.ofNullable(user);
        } catch(Exception e) {
            if(this.entityManager.getTransaction() != null) { this.entityManager.getTransaction().rollback(); }
            logger.info(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<User>> getUsersLike(String pattern) {
        try {
            this.entityManager.getTransaction().begin();

            Query namedQuery = this.entityManager.createNamedQuery(User.GET_USERS_LIKE);
            namedQuery.setParameter("pattern", "%" + pattern + "%");
            List<User> users = namedQuery.getResultList();

            this.entityManager.getTransaction().commit();

            return Optional.ofNullable(users);
        } catch(Exception e) {
            if(this.entityManager.getTransaction() != null) { this.entityManager.getTransaction().rollback(); }
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }
}
