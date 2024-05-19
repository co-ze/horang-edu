package hanghackaton.horanedu.domain.board.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.domain.board.dto.PatchPostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostResponseDto;
import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.board.postEnum.PostCategoryEnum;
import hanghackaton.horanedu.domain.board.repository.post.PostRepository;
import hanghackaton.horanedu.domain.user.entity.User;
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
import org.springframework.util.StringUtils;

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

    //게시글 생성
    @Transactional
    public ResponseDto<String> createPost(PostRequestDto postRequestDto, User user) throws IOException {

        log.info("-----CREATE POST START-----");

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );

        //요청 Dto에서 제목 가져오기
        String title = postRequestDto.getTitle();
        //게시글 객체 생성 메서드
        Post post = makePost(postRequestDto, user, title);
        //게시글 유저 연관관계
        post.setUser(userNow);
        //유저의 postList에 게시글 추가
        userNow.addPost(post);
        postRepository.save(post);

        log.info("-----CREATE POST END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "게시물 생선 완료!");
    }


    //게시물 조회
    @Transactional
    public ResponseDto<PostResponseDto> getPost(Long id) {

        log.info("-----READ POST DETAIL START-----");

        //게시글이 존재하는지 확인 및 데이터 베이스에서 가져오기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );

        //조회수 올리기 (QueryDsl)
        postRepository.increaseViews(id);

        PostResponseDto postResponseDto = new PostResponseDto(post);

        log.info("-----READ POST DETAIL END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "게시물 " + id + "번", postResponseDto);
    }

    @Transactional
    public ResponseDto<String> updatePost(Long id,
                                          User user,
                                          PatchPostRequestDto patchPostRequestDto) throws IOException {

        log.info("-----UPDATE POST START-----");

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //PathValue로 게시글 찾기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );
        //유저가 작성한 게시글인지 확인
        if (!Objects.equals(userNow.getId(), post.getUser().getId())) {
            throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
        }

        //요청 데이터가 존재하면 set
        if (patchPostRequestDto.getTitle() != null) post.setTitle(patchPostRequestDto.getTitle());
        if (patchPostRequestDto.getContent() != null) post.setContent(patchPostRequestDto.getContent());

        postRepository.save(post);

        log.info("-----UPDATE POST END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "게시글 수정");

    }

    @Transactional
    public ResponseDto<String> deletePost(Long id,
                                          User user) {

        log.info("-----DELETE POST START-----");

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //PathValue로 게시글 찾기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_POST)
        );
        //유저가 작성한 게시글인지 확인
        if (!Objects.equals(userNow.getId(), post.getUser().getId())) {
            throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
        }

        postRepository.delete(post);

        log.info("-----DELETE POST END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "게시글 삭제");
    }

    @Transactional(readOnly = true)
    public ResponseDto<Page<PostResponseDto>> getPostList(int page, int size, String category) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(page, size, sort);
        PostCategoryEnum categoryEnum;

        if(StringUtils.pathEquals(category, "FREE")){
            categoryEnum = PostCategoryEnum.FREE;
        }else if(StringUtils.pathEquals(category, "QUESTION")){
            categoryEnum = PostCategoryEnum.QUESTION;
        }else{
            categoryEnum = PostCategoryEnum.DIARY;
        }


        Page<PostResponseDto> result = postRepository.searchPosts(pageable, categoryEnum);

        return ResponseDto.set(HttpStatus.OK, "전체검색", result);
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
