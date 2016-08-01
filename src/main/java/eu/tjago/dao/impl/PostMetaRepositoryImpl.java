package eu.tjago.dao.impl;

import eu.tjago.dao.PostMetaRepository;
import eu.tjago.entity.PostMeta;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * Created by jagod on 30/07/2016.
 */
public class PostMetaRepositoryImpl extends GenericDaoImpl implements PostMetaRepository {

    public PostMetaRepositoryImpl() {
        super(PostMeta.class);
    }

    @Override
    public Optional<String> getOneForPost(Long postId, String key) {
        return null;
    }


    @Override
    public Optional<List<PostMeta>> getAllForPost(Long postId) {
        try {
            this.entityManager.getTransaction().begin();

            Query query = entityManager.createNamedQuery("select e from " + entityClass.getName() + " as e WHERE postId = :id");
            query.setParameter("postId", postId);

            @SuppressWarnings("unchecked")
            List<PostMeta> listOfEntities = (List<PostMeta>) query.getResultList();

            this.entityManager.getTransaction().commit();

            return Optional.ofNullable(listOfEntities);
        } catch(Exception e) {
            if(entityManager.getTransaction() != null) { entityManager.getTransaction().rollback(); }
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }
}
