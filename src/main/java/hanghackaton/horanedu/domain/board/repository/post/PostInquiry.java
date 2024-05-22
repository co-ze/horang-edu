package hanghackaton.horanedu.domain.board.repository.post;

import hanghackaton.horanedu.domain.board.dto.PostResponseDto;
import hanghackaton.horanedu.domain.board.dto.SimPostResDto;
import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.board.postEnum.PostCategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostInquiry {

    void increaseViews(Long id);

    Page<SimPostResDto> searchPosts(Pageable pageable, PostCategoryEnum category);
}
