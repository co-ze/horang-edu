package hanghackaton.horanedu.domain.board.repository.post;

import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.board.postEnum.PostCategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostInquiry{

}
