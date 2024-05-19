package hanghackaton.horanedu.domain.board.entity;

import hanghackaton.horanedu.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class ClassPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private String gradeClass;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime created;

    @ColumnDefault("0")
    private Integer views;

    @Column(nullable = false)
    private String userName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ClassPost(String title, String content, String gradeClass, String userName) {
        this.title = title;
        this.content = content;
        this.gradeClass = gradeClass;
        this.userName = userName;
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
