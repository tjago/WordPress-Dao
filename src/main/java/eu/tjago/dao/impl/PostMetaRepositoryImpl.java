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
    public Optional<PostMeta> getOneForPost(Long postId, String key) {
        try {
            this.entityManager.getTransaction().begin();

            Query query = entityManager.createNamedQuery(PostMeta.GET_POSTMETA_BY_KEY);
            query.setParameter("postId", postId);
            query.setParameter("key", key);

            @SuppressWarnings("unchecked")
            PostMeta postMeta = (PostMeta) query.getSingleResult();

            this.entityManager.getTransaction().commit();

            return Optional.ofNullable(postMeta);
        } catch(Exception e) {
            if(entityManager.getTransaction() != null) { entityManager.getTransaction().rollback(); }
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getPostMataValue(Long postId, String key) {
        Optional<PostMeta> postMeta = getOneForPost(postId, key);
        if (postMeta.isPresent()) {
            return Optional.of(postMeta.get().getValue());
        } else {
            return Optional.empty();
        }
    }


    @Override
    public Optional<List<PostMeta>> getAllForPost(Long postId) {
        try {
            this.entityManager.getTransaction().begin();

            Query query = entityManager.createNamedQuery(PostMeta.GET_ALL_POSTMETA);
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
