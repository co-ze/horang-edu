package hanghackaton.horanedu.domain.board.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.common.s3.S3Service;
import hanghackaton.horanedu.domain.board.dto.PostCreateDto;
import hanghackaton.horanedu.domain.board.dto.PostResponseDto;
import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.board.repository.PostRepository;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final S3Service s3Service;

    @Transactional
    public ResponseDto<String> createPost(PostCreateDto postCreateDto, List<MultipartFile> images, User user) throws IOException {

        String title = postCreateDto.getTitle();
        String content = postCreateDto.getContent();
        String userName = user.getName();

        String imagesToString = s3Service.uploadFiles(images);

        Post post = new Post(title, content, userName, imagesToString);
        postRepository.save(post);

        return ResponseDto.setSuccess(HttpStatus.OK, "게시물 생선 완료!");
    }

    @Transactional
    public ResponseDto<PostResponseDto> getPost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );

        postRepository.increaseViews(id);

        PostResponseDto postResponseDto = new PostResponseDto(post);

        return ResponseDto.setSuccess(HttpStatus.OK, "게시물 " + id + "번", postResponseDto);
    }

}
