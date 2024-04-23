package hanghackaton.horanedu.domain.board.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.common.s3.S3Service;
import hanghackaton.horanedu.domain.board.dto.ClassPostResponseDto;
import hanghackaton.horanedu.domain.board.dto.PatchPostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostRequestDto;
import hanghackaton.horanedu.domain.board.entity.ClassPost;
import hanghackaton.horanedu.domain.board.entity.PostImage;
import hanghackaton.horanedu.domain.board.repository.classPost.ClassPostRepository;
import hanghackaton.horanedu.domain.board.repository.postImage.PostImageRepository;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassPostService {

    private final UserRepository userRepository;
    private final ClassPostRepository classPostRepository;
    private final PostImageRepository postImageRepository;
    private final S3Service s3Service;

    //학급 게시물 생성
    @Transactional
    public ResponseDto<String> createClassPost(PostRequestDto postRequestDto, List<MultipartFile> requestImages, User user) throws IOException {

        log.info("-----CREATE CLASS POST START-----");

        User userNow = userRepository.findById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );

        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        String gradeClass = userNow.getUserDetail().getGrade() + "-"; //userNow.getUserDetail().getClassNum();
        ClassPost classPost = new ClassPost(title, content, gradeClass, userNow.getName());

        List<PostImage> images = new ArrayList<>();

        if (!requestImages.isEmpty()) {
            images = s3Service.uploadPostImages(requestImages, classPost);
        }

        classPost.setImage(images);
        classPost.setUser(userNow);
        userNow.addClassPost(classPost);
        classPostRepository.save(classPost);

        log.info("-----CREATE CLASS POST END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "학급 게시물 생성");

    }

    @Transactional(readOnly = true)
    public ResponseDto<ClassPostResponseDto> getClassPost(Long id) {

        log.info("-----READ CLASS POST START-----");

        ClassPost classPost = classPostRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );

        ClassPostResponseDto classPostResponseDto = new ClassPostResponseDto(classPost);

        log.info("-----READ CLASS POST END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "학급 게시물 " + id + "번 조회", classPostResponseDto);

    }

    @Transactional
    public ResponseDto<String> updateClassPost(Long id,
                                               User user,
                                               PatchPostRequestDto patchPostRequestDto,
                                               List<MultipartFile> requestImages) throws IOException {

        log.info("-----UPDATE CLASS POST START-----");

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //학급 게시글 찾기
        ClassPost classPost = classPostRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );
        //유저가 작성한 게시글인지 확인
        if (!Objects.equals(userNow.getId(), classPost.getUser().getId())) {
            throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
        }

        //요청 데이터가 존재하면 set
        if (patchPostRequestDto.getTitle() != null) classPost.setTitle(patchPostRequestDto.getTitle());
        if (patchPostRequestDto.getContent() != null) classPost.setContent(patchPostRequestDto.getContent());
        //이미지 요청 데이터가 있는지 확인
        if (!requestImages.isEmpty()) {
            //게시글 이미지 삭제 준비
            List<PostImage> postImages = classPost.getImages();
            //리스트에 포함된 이미지 모두 삭제
            postImageRepository.deleteAll(postImages);
            //aws s3 이미지 업로드
            List<PostImage> uploadedImages = new ArrayList<>();
            uploadedImages = s3Service.uploadPostImages(requestImages, classPost);
            //게시글 이미지 set
            classPost.setImage(uploadedImages);
            classPostRepository.save(classPost);
        }

        log.info("-----UPDATE CLASS POST END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "학급 게시글 수정");

    }

    @Transactional
    public ResponseDto<String> deleteClassPost(Long id, User user) {

        log.info("-----DELETE CLASS POST START-----");

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //학급 게시글 찾기
        ClassPost classPost = classPostRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );
        //유저가 작성한 게시글인지 확인
        if (!Objects.equals(userNow.getId(), classPost.getUser().getId())) {
            throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
        }

        classPostRepository.delete(classPost);

        log.info("-----DELETE CLASS POST END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "학급 게시글 삭제");

    }

}
