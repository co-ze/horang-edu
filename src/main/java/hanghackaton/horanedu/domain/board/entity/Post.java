package hanghackaton.horanedu.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import hanghackaton.horanedu.domain.board.postEnum.PostCategoryEnum;
import hanghackaton.horanedu.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PostCategoryEnum category;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime created;

    @ColumnDefault("0")
    private Integer views;

    @Column(nullable = false)
    private String userName;

    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String content, PostCategoryEnum category, String userName) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.userName = userName;
    }

    public void setImage(List<PostImage> postImage) {
        this.images = postImage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
