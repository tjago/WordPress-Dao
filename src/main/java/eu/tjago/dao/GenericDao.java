package eu.tjago.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by jagod on 31/07/2016.
 */
public interface GenericDao<T, PK extends Serializable> {

    T create(T t);

    Optional<T> read(PK id);

    Optional<List<T>> readAll();

    T update(T t);

    void delete(T t);
}
