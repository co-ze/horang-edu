package hanghackaton.horanedu.domain.board.dto;

import hanghackaton.horanedu.domain.board.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String userName;
    private LocalDateTime created;
    private Integer views;
    private String email;

    public PostResponseDto(Post post, String email) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userName = post.getUserName();
        this.created = post.getCreated();
        this.views = post.getViews();
        this.email = email;
    }
}
