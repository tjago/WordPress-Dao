package eu.tjago.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by tjago on 2016-04-13.
 */
@Entity
@Table(name = "wp_usermeta")

@NamedQueries({
        @NamedQuery(
                name = UserMeta.GET_ALL_USERMETA,
                query = "SELECT um FROM UserMeta AS um WHERE um.userId = :userId"
        ),
        @NamedQuery(
                name = UserMeta.GET_USERMETA_BY_KEY,
                query = "SELECT um FROM UserMeta AS um WHERE um.userId = :userId AND um.key = :key"
        )
//        ,
//        @NamedQuery(
//                name = UserMeta.SET_USERMETA,
//                query = "INSERT um FROM UserMeta AS um WHERE um.userId = :userId AND um.key = :key"
//        )
})
public class UserMeta {

    public static final String GET_ALL_USERMETA = "getAllUserMeta";
    public static final String GET_USERMETA_BY_KEY = "getUserMetaByKey";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "umeta_id")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "meta_key")
    private String key;

    @Column(name = "meta_value")
    private String value;

    public UserMeta() {

    }

    public UserMeta(Long userId, String key, String value) {
        this.userId = userId;
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMeta userMeta = (UserMeta) o;
        return Objects.equals(id, userMeta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserMeta{" +
                "value='" + value + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
