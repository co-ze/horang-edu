package hanghackaton.horanedu.domain.board.dto;

import hanghackaton.horanedu.domain.board.entity.ClassPost;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SimCPostResDto {
    private Long id;
    private String title;
    private String content;
    private String userName;
    private String gradeClass;
    private LocalDateTime created;

    public SimCPostResDto(ClassPost classPost) {
        this.id = classPost.getId();
        this.title = classPost.getTitle();
        this.content = classPost.getContent();
        this.userName = classPost.getUserName();
        this.gradeClass = classPost.getGradeClass();
        this.created = classPost.getCreated();
    }
}
