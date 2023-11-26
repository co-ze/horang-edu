package hanghackaton.horanedu.domain.board.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.common.s3.S3Service;
import hanghackaton.horanedu.domain.board.dto.PatchPostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostResponseDto;
import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.board.entity.PostImage;
import hanghackaton.horanedu.domain.board.postEnum.PostCategoryEnum;
import hanghackaton.horanedu.domain.board.repository.classPost.ClassPostRepository;
import hanghackaton.horanedu.domain.board.repository.post.PostRepository;
import hanghackaton.horanedu.domain.board.repository.postImage.PostImageRepository;
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
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final S3Service s3Service;
    private final PostImageRepository postImageRepository;
    private final ClassPostRepository classPostRepository;

    //게시글 생성
    @Transactional
    public ResponseDto<String> createPost(PostRequestDto postRequestDto, List<MultipartFile> requestImages, User user) throws IOException {

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );

        //요청 Dto에서 제목 가져오기
        String title = postRequestDto.getTitle();
        //게시글 객체 생성 메서드
        Post post = makePost(postRequestDto, user, title);

        //사진 리스트 준비
        List<PostImage> images = new ArrayList<>();

        //요청 데이터에 사진이 있는지 확인
        if (!requestImages.isEmpty()) {
            //aws s3 서비스 사진 업로드 로직
            images = s3Service.uploadPostImages(requestImages, post);
        }

        //게시글에 이미지를 set
        post.setImage(images);
        //게시글 유저 연관관계
        post.setUser(userNow);
        //유저의 postList에 게시글 추가
        userNow.addPost(post);
        postRepository.save(post);

        return ResponseDto.setSuccess(HttpStatus.OK, "게시물 생선 완료!");
    }



    //게시물 조회
    @Transactional
    public ResponseDto<PostResponseDto> getPost(Long id) {

        //게시글이 존재하는지 확인 및 데이터 베이스에서 가져오기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );

        //조회수 올리기 (QueryDsl)
        postRepository.increaseViews(id);

        PostResponseDto postResponseDto = new PostResponseDto(post);

        return ResponseDto.setSuccess(HttpStatus.OK, "게시물 " + id + "번", postResponseDto);
    }

    @Transactional
    public ResponseDto<String> updatePost(Long id,
                                          User user,
                                          PatchPostRequestDto patchPostRequestDto,
                                          List<MultipartFile> requestImages) throws IOException {

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //PathValue로 게시글 찾기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );
        //유저가 작성한 게시글인지 확인
        if (Objects.equals(userNow.getId(), post.getUser().getId())) {
            throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
        }

        //요청 데이터가 존재하면 set
        if (patchPostRequestDto.getTitle() != null) post.setTitle(patchPostRequestDto.getTitle());
        if (patchPostRequestDto.getContent() != null) post.setContent(patchPostRequestDto.getContent());
        //이미지 요청 데이터가 있는지 확인
        if (!requestImages.isEmpty()){
            //게시글 이미지 삭제 준비
            List<PostImage> postImages = post.getImages();
            //리스트에 포함된 이미지 모두 삭제
            postImageRepository.deleteAll(postImages);
            //aws s3 이미지 업로드
            List<PostImage> uploadedImages = new ArrayList<>();
            uploadedImages = s3Service.uploadPostImages(requestImages, post);
            //게시글 이미지 set
            post.setImage(uploadedImages);
            postRepository.save(post);
        }

        return ResponseDto.setSuccess(HttpStatus.OK, "게시글 수정");

    }

    @Transactional
    public ResponseDto<String> deletePost(Long id,
                                          User user) {

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //PathValue로 게시글 찾기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );
        //유저가 작성한 게시글인지 확인
        if (Objects.equals(userNow.getId(), post.getUser().getId())) {
            throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
        }

        postRepository.delete(post);

        return ResponseDto.setSuccess(HttpStatus.OK, "게시글 삭제");
    }

    //게시글 객체 생성 메서드
    private Post makePost(PostRequestDto postRequestDto, User user, String title) {
        String content = postRequestDto.getContent();
        String userName = user.getName();
        String requestCategory = postRequestDto.getCategory();
        PostCategoryEnum category;

        //카테고리 부여
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
