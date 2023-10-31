package hanghackaton.horanedu.domain.board.dto;

import hanghackaton.horanedu.domain.board.entity.PostImage;
import lombok.Getter;

@Getter
public class PostImageResponseDto {

    private Long id;
    private String image;

    public PostImageResponseDto(PostImage postImage) {
        this.id = postImage.getId();
        this.image = postImage.getImage();
    }
}
