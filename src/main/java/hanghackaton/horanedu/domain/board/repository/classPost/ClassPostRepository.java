package hanghackaton.horanedu.domain.board.repository.classPost;

import hanghackaton.horanedu.domain.board.entity.ClassPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassPostRepository extends JpaRepository<ClassPost, Long> {

}
