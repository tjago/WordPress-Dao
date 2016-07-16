package eu.tjago.entity;

import eu.tjago.enums.UserStatus;
import eu.tjago.util.AccountActivationUtil;
import eu.tjago.util.PasswordUtil;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by tjago on 2016-04-06.
 */
@Entity
@Table(name = "wp_users")
@NamedQueries({
        @NamedQuery(
                name = User.GET_ALL_USERS,
                query = "select u from User as u"
        ),
        @NamedQuery(
                name = User.GET_USER_BY_ID,
                query = "select u from User as u WHERE id = :userId"
        ),
        @NamedQuery(
                name = User.GET_USER_BY_NAME,
                query = "select u from User as u WHERE login = :name"
        ),
        @NamedQuery(
                name = User.GET_USERS_LIKE,
                query = "select u from User as u WHERE (u.login LIKE :pattern OR u.nicename LIKE :pattern)"
        ),
})
public class User {

    public final static String GET_ALL_USERS    = "getAllUsers";
    public final static String GET_USER_BY_ID   = "getUserById";
    public final static String GET_USER_BY_NAME = "getUserByName";
    public static final String GET_USERS_LIKE   = "getUsersLike";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_login", length = 60, nullable = false) // == username in WP-admin
    private String login;

    @Column(name = "user_pass", length = 64, nullable = false)
    private String password;

    @Column(name = "user_nicename", length = 50, nullable = false)
    private String nicename = "";

    @Column(name = "user_email", length = 100, nullable = false)
    private String email;

    @Column(name = "user_url", length = 100, nullable = false)
    private String url = "";

    //set SYSDATE from dbms-side
    @Column(name = "user_registered", nullable = false)
    private LocalDateTime registred;

    @Column(name = "user_activation_key", length = 60, nullable = false)
    private String activationKey;

    @Column(name = "user_status", nullable = false)
    private Integer status = UserStatus.HAM.getValue();

    @Column(name = "display_name", length = 250, nullable = false)
    private String displayName = "";

    @OneToMany(mappedBy = "userId", cascade = CascadeType.REMOVE)
    List<UserMeta> userMeta = new ArrayList<>();

    public User() {
    }

    public User(String username, String email) {
        this(username, email, PasswordUtil.generatePassword());
    }

    public User(String username, String email, String password) {

        this.login = username;
        this.password = PasswordUtil.hashPassword(password);
        this.email = email;
        this.activationKey = AccountActivationUtil.generateActivationKey();
        this.registred = LocalDateTime.now();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getRegistered() {
        return registred;
    }

    public void setRegistred(LocalDateTime registred) {
        this.registred = registred;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<UserMeta> getUserMeta() {
        return userMeta;
    }

    public void setUserMeta(List<UserMeta> userMeta) {
        this.userMeta = userMeta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", registered=" + registred +
                ", login='" + login + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
