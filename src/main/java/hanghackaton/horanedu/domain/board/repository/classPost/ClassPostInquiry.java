package hanghackaton.horanedu.domain.board.repository.classPost;

import hanghackaton.horanedu.domain.board.dto.ClassPostResponseDto;
import hanghackaton.horanedu.domain.board.dto.SimCPostResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassPostInquiry {
    Page<SimCPostResDto> searchClassPosts(Pageable pageable, String grade);
}
