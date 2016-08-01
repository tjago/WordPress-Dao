package eu.tjago.entity;

import eu.tjago.listener.GeneralEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by tjago on 17.07.2016.
 */

@Entity
@EntityListeners(GeneralEntityListener.class)
@Table(name = "wp_posts")
@NamedQueries({
        @NamedQuery(
                name = Post.GET_POST_BY_ID,
                query = "select p from Post as p WHERE id = :postId"
        ),
})
public class Post {

    public final static String GET_POST_BY_ID = "getPostById";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name ="post_author", nullable = false)
//    @Column(name ="post_author")
    private User author;

    @Column(name = "post_date", nullable = false)
    private LocalDateTime postDate;

    @Column(name = "post_date_gmt", nullable = false)
    private LocalDateTime postDateGmt;

    @Column(name = "post_content", nullable = false)
    private String content = "";

    @Column(name = "post_title", nullable = false)
    private String title = "";

    @Column(name = "post_excerpt", nullable = false)
    private String excerpt = "";

    @Column(name = "post_status", length = 20, nullable = false)
    private String postStatus = "";

    @Column(name = "comment_status", length = 20, nullable = false)
    private String commentStatus = "";

    @Column(name = "post_name", length = 200, nullable = false)
    private String postName = "";

    @Column(name = "to_ping", nullable = false)
    private String toPing = "";

    @Column(name = "pinged", nullable = false)
    private String pinged = "";

    @Column(name = "post_modified", nullable = false)
    private LocalDateTime modified = LocalDateTime.MIN;

    @Column(name = "post_modified_gmt", nullable = false)
    private LocalDateTime modifiedGmt = LocalDateTime.MIN;

    @Column(name = "post_content_filtered", nullable = false)
    private String contentFiltered = "";

    @OneToOne
    @JoinColumn(name = "id", nullable = false)
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @MapKeyColumn(name = "post_parent")
//    @Column(name = "post_parent", nullable = false, insertable = false)
    private Post parent;

    @Column(name = "guid", length = 255, nullable = false)
    private String guid = "";

    @Column(name = "menu_order", nullable = false)
    private Integer menuOrder = 0;

    @Column(name = "post_type", length = 20, nullable = false)
    private String type = "";

    @Column(name = "post_mime_type", length = 100, nullable = false)
    private String mimeType = "";

    @Column(name = "comment_count", nullable = false)
    private Long commentCount = 0L;


    public Post() {
    }

    public Post(String content, String title, User author) {
        this.content = content;
        this.title = title;
        this.author = author;
        this.postDate = LocalDateTime.now();
        this.postDateGmt = this.postDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", author=" + author.getEmail() +
                ", postDate=" + postDate +
                '}';
    }
}
