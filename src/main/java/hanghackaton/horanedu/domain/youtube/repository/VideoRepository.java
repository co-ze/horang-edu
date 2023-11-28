package hanghackaton.horanedu.domain.youtube.repository;

import hanghackaton.horanedu.domain.youtube.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByUserId(Long id);
}
