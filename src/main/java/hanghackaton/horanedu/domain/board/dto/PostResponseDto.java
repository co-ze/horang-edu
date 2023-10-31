package hanghackaton.horanedu.domain.board.dto;

import hanghackaton.horanedu.domain.board.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private String title;
    private String content;
    private String userName;
    private LocalDateTime created;
    private Integer views;
    private List<PostImageResponseDto> images;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userName = post.getUserName();
        this.created = post.getCreated();
        this.views = post.getViews();
        this.images = post.getImages().stream().map(PostImageResponseDto::new).toList();
    }
}
