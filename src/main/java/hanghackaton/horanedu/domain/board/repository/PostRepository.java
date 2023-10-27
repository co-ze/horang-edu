package hanghackaton.horanedu.domain.board.repository;

import hanghackaton.horanedu.domain.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostInquiry{

}
