package eu.tjago.dao.impl;

import eu.tjago.dao.PostMetaRepository;
import eu.tjago.entity.PostMeta;

import java.util.List;

/**
 * Created by jagod on 30/07/2016.
 */
public class PostMetaRepositoryImpl extends GenericDaoImpl implements PostMetaRepository {

    public PostMetaRepositoryImpl() {
        super(PostMeta.class);
    }

    @Override
    public String getPostMetaByKey(String key, Long postId) {
        return null;
    }
}
