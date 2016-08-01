package eu.tjago.listener;

import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * Created by jagod on 01/08/2016.
 */
public class GeneralEntityListener {


    Logger logger = Logger.getLogger(this.getClass());

    @PrePersist
    void onPrePersist(Object o) {}

    @PostPersist
    void onPostPersist(Object o) {
        logger.info("Entity saved: " + o.toString());
    }

    @PostLoad
    void onPostLoad(Object o) {}

    @PreUpdate
    void onPreUpdate(Object o) {}

    @PostUpdate
    void onPostUpdate(Object o) {}

    @PreRemove
    void onPreRemove(Object o) {

    }

    @PostRemove
    void onPostRemove(Object o) {
        logger.info("Entity removed: " + o.toString());
    }
}
