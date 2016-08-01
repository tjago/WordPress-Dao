package eu.tjago.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by tjago on 2016-07-30.
 */
@Entity
@Table(name = "wp_postmeta")

@NamedQueries({
        @NamedQuery(
                name = PostMeta.GET_ALL_POSTMETA,
                query = "SELECT um FROM PostMeta AS um WHERE um.postId = :postId"
        ),
        @NamedQuery(
                name = PostMeta.GET_POSTMETA_BY_KEY,
                query = "SELECT um FROM PostMeta AS um WHERE um.postId = :postId AND um.key = :key"
        )
//        ,
//        @NamedQuery(
//                name = UserMeta.SET_USERMETA,
//                query = "INSERT um FROM UserMeta AS um WHERE um.userId = :userId AND um.key = :key"
//        )
})
public class PostMeta {

    public static final String GET_ALL_POSTMETA = "getAllPostMeta";
    public static final String GET_POSTMETA_BY_KEY = "getPostMetaByKey";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "meta_id")
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "meta_key")
    private String key;

    @Column(name = "meta_value")
    private String value;

    public PostMeta() {

    }

    public PostMeta(Long postId, String key, String value) {
        this.postId = postId;
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostMeta postMeta = (PostMeta) o;
        return Objects.equals(id, postMeta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PostMeta{" +
                "value='" + value + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
