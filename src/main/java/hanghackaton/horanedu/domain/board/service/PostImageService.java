package hanghackaton.horanedu.domain.board.service;

import hanghackaton.horanedu.domain.board.entity.PostImage;
import hanghackaton.horanedu.domain.board.repository.postImage.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;

    @Transactional
    public PostImage createPostImage(String image) {

        PostImage postImage = new PostImage(image);
        postImageRepository.save(postImage);

        return postImage;
    }

}
