package hanghackaton.horanedu.domain.board.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.common.s3.S3Service;
import hanghackaton.horanedu.domain.board.dto.PostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostResponseDto;
import hanghackaton.horanedu.domain.board.entity.ClassPost;
import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.board.entity.PostImage;
import hanghackaton.horanedu.domain.board.postEnum.PostCategoryEnum;
import hanghackaton.horanedu.domain.board.repository.classPost.ClassPostRepository;
import hanghackaton.horanedu.domain.board.repository.post.PostRepository;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final S3Service s3Service;
    private final ClassPostRepository classPostRepository;

    //게시글 생성
    @Transactional
    public ResponseDto<String> createPost(PostRequestDto postRequestDto, List<MultipartFile> requestImages, User user) throws IOException {

        User userNow = userRepository.findById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );

        String title = postRequestDto.getTitle();
        Post post = makePost(postRequestDto, user, title);
        log.info(post.getCategory().toString());

        List<PostImage> images = new ArrayList<>();

        if (!requestImages.isEmpty()) {
            images = s3Service.uploadPostImages(requestImages, post);
        }

        post.setImage(images);
        post.setUser(userNow);
        userNow.addPost(post);
        postRepository.save(post);

        return ResponseDto.setSuccess(HttpStatus.OK, "게시물 생선 완료!");
    }



    //게시물 조회
    @Transactional
    public ResponseDto<PostResponseDto> getPost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );

        postRepository.increaseViews(id);

        PostResponseDto postResponseDto = new PostResponseDto(post);

        return ResponseDto.setSuccess(HttpStatus.OK, "게시물 " + id + "번", postResponseDto);
    }

    @Transactional
    public ResponseDto<String> updatePost(Long id,
                                          User user,
                                          PostRequestDto postRequestDto,
                                          List<MultipartFile> images) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );



        return ResponseDto.setSuccess(HttpStatus.OK, "");

    }

    @Transactional
    public ResponseDto<String> deletePost(Long id) {

        postRepository.deleteById(id);

        return ResponseDto.setSuccess(HttpStatus.OK, "게시글 삭제링~");
    }

    private Post makePost(PostRequestDto postRequestDto, User user, String title) {
        String content = postRequestDto.getContent();
        String userName = user.getName();
        String requestCategory = postRequestDto.getCategory();
        PostCategoryEnum category;

        if (StringUtils.pathEquals(requestCategory, "FREE")) {
            category = PostCategoryEnum.FREE;
        } else if (StringUtils.pathEquals(requestCategory, "QUESTION")) {
            category = PostCategoryEnum.QUESTION;
        } else {
            category = PostCategoryEnum.DIARY;
        }

        return new Post(title, content, category, userName);
    }

}
