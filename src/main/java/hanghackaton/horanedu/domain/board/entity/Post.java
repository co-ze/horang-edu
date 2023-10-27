package hanghackaton.horanedu.domain.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Blob;
import java.time.LocalDateTime;

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

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime created;

    @ColumnDefault("0")
    private Integer views;

    @Column(nullable = false)
    private String userName;

    private String images;

    public Post(String title, String content, String userName, String images) {
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.images = images;
    }

}
