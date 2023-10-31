package hanghackaton.horanedu.domain.board.repository.postImage;

import hanghackaton.horanedu.domain.board.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
