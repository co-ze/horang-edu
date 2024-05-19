package hanghackaton.horanedu.domain.board.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.domain.board.dto.ClassPostResponseDto;
import hanghackaton.horanedu.domain.board.dto.PatchPostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostResponseDto;
import hanghackaton.horanedu.domain.board.entity.ClassPost;
import hanghackaton.horanedu.domain.board.postEnum.PostCategoryEnum;
import hanghackaton.horanedu.domain.board.repository.classPost.ClassPostRepository;
import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassPostService {

    private final UserRepository userRepository;
    private final ClassPostRepository classPostRepository;

    //학급 게시물 생성
    @Transactional
    public ResponseDto<String> createClassPost(PostRequestDto postRequestDto, User user) throws IOException {

        log.info("-----CREATE CLASS POST START-----");

        User userNow = userRepository.findById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        UserDetail userDetail = userNow.getUserDetail();
        School group = userDetail.getSchool();
        if(group == null) throw new GlobalException(ExceptionEnum.NOT_FOUND_GRADE);

        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        String gradeClass = group.getGrade();
        ClassPost classPost = new ClassPost(title, content, gradeClass, userNow.getName());

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
                                               PatchPostRequestDto patchPostRequestDto) throws IOException {

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

        classPostRepository.save(classPost);

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

    @Transactional(readOnly = true)
    public ResponseDto<Page<ClassPostResponseDto>> getClassPostList(int page, int size, User loginUser) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(page, size, sort);
        User userNow = userRepository.findById(loginUser.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        UserDetail userDetail = userNow.getUserDetail();
        School group = userDetail.getSchool();
        if(group == null) throw new GlobalException(ExceptionEnum.NOT_JOIN_GROUP);


        Page<ClassPostResponseDto> result = classPostRepository.searchClassPosts(pageable, group.getGrade());

        return ResponseDto.set(HttpStatus.OK, "학급 게시판 조회", result);
    }
}
