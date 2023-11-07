package hanghackaton.horanedu.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Getter
@NoArgsConstructor
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "classPost_id")
    private ClassPost classPost;

    public PostImage(String image) {
        this.image = image;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setClassPost(ClassPost classPost) {
        this.classPost = classPost;
    }
}
