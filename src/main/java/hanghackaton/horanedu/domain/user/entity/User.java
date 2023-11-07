package hanghackaton.horanedu.domain.user.entity;

import hanghackaton.horanedu.domain.board.entity.ClassPost;
import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.user.userEnum.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetail userDetail;

    private Long kakaoId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ClassPost> classPosts;

    public User(String email, String name, String password, UserRole role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(String email, String name, String password, UserRole role, Long kakaoId) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void setKakaoId(Long id) {
        this.kakaoId = id;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void addClassPost(ClassPost classPost) {
        this.classPosts.add(classPost);
    }
}
