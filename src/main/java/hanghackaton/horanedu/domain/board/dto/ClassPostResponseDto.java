package hanghackaton.horanedu.domain.board.dto;

import hanghackaton.horanedu.domain.board.entity.ClassPost;
import hanghackaton.horanedu.domain.board.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ClassPostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String userName;
    private String gradeClass;
    private LocalDateTime created;
    private Integer views;
    private List<PostImageResponseDto> images;

    public ClassPostResponseDto(ClassPost classPost) {
        this.id = classPost.getId();
        this.title = classPost.getTitle();
        this.content = classPost.getContent();
        this.userName = classPost.getUserName();
        this.gradeClass = classPost.getGradeClass();
        this.created = classPost.getCreated();
        this.views = classPost.getViews();
        this.images = classPost.getImages().stream().map(PostImageResponseDto::new).toList();;
    }
}
