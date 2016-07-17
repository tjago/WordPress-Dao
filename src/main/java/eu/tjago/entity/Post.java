package eu.tjago.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by tjago on 17.07.2016.
 */

@Entity
@Table(name = "wp_posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="post_author")
    private User author;

    @Column(name = "post_date")
    private LocalDateTime postDate;

    @Column(name = "post_date_gmt")
    private LocalDateTime postDateGmt;

    @Column(name = "post_content")
    private String content;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_excerpt")
    private String excerpt;

    @Column(name = "post_status", length = 20)
    private String postStatus;

    @Column(name = "comment_status", length = 20)
    private String commentStatus;

    @Column(name = "post_name", length = 200)
    private String postName;

    @Column(name = "to_ping")
    private String toPing;

    @Column(name = "pinged")
    private String pinged;

    @Column(name = "post_modified")
    private LocalDateTime modified;

    @Column(name = "post_modified_gmt")
    private LocalDateTime modifiedGmt;

    @Column(name = "post_content_filtered")
    private String contentFiltered;

    @Column(name = "post_parent")
    private Post parent;

    @Column(name = "guid", length = 255)
    private String guid;

    @Column(name = "menu_order")
    private int menuOrder;

    @Column(name = "post_type", length = 20)
    private String type;

    @Column(name = "post_mime_type", length = 100)
    private String mimeType;

    @Column(name = "comment_count")
    private String commentCount;


    public Post() {
    }

    public Post(String content, String title, User author) {
        this.content = content;
        this.title = title;
        this.author = author;
        this.postDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
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

    public User getAuthor() {
        return author;
    }
}
