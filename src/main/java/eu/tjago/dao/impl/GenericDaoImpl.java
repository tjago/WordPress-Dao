package eu.tjago.dao.impl;

import eu.tjago.dao.GenericDao;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by jagod on 31/07/2016.
 */
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected Class<T> entityClass;

    Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    protected EntityManager entityManager;

    public GenericDaoImpl(Class<T> type) {
        this.entityClass = type;

        //FIXME Those lines are only needed when there is no CDI support
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("wordpress-dao");
        entityManager = emf.createEntityManager();
    }

    @Override
    public T create(T t) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(t);
            this.entityManager.getTransaction().commit();
        } catch(Exception e) {
            if(entityManager.getTransaction() != null) { entityManager.getTransaction().rollback(); }
            logger.error(e.getMessage());
        }
        return t;
    }

    @Override
    public Optional<T> read(PK id) {
        try {
            this.entityManager.getTransaction().begin();
            T t = this.entityManager.find(entityClass, id);
            this.entityManager.getTransaction().commit();
            return Optional.ofNullable(t);
        } catch(Exception e) {
            if(entityManager.getTransaction() != null) { entityManager.getTransaction().rollback(); }
            logger.error(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<T>> readAll() {

        try {
            this.entityManager.getTransaction().begin();

            Query query = entityManager.createNamedQuery("select e from " + entityClass.getName());

            @SuppressWarnings("unchecked")
            List<T> listOfEntities = (List<T>) query.getResultList();

            this.entityManager.getTransaction().commit();

            return Optional.ofNullable(listOfEntities);
        } catch(Exception e) {
            if(entityManager.getTransaction() != null) { entityManager.getTransaction().rollback(); }
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public T update(T t) {
        try {
            this.entityManager.getTransaction().begin();
            t = this.entityManager.merge(t);
            this.entityManager.getTransaction().commit();
        } catch(Exception e) {
            if(entityManager.getTransaction() != null) { entityManager.getTransaction().rollback(); }
            logger.error(e.getMessage());
        }
        return t;
    }

    @Override
    public void delete(T t) {
        try {
            this.entityManager.getTransaction().begin();
            t = this.entityManager.merge(t);
            this.entityManager.remove(t);
            this.entityManager.getTransaction().commit();
        } catch(Exception e) {
            if(entityManager.getTransaction() != null) { entityManager.getTransaction().rollback(); }
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        entityManager.close();
    }
}
