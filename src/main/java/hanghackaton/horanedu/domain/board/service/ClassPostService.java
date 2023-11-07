package hanghackaton.horanedu.domain.board.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.common.s3.S3Service;
import hanghackaton.horanedu.domain.board.dto.PostRequestDto;
import hanghackaton.horanedu.domain.board.entity.ClassPost;
import hanghackaton.horanedu.domain.board.entity.PostImage;
import hanghackaton.horanedu.domain.board.repository.classPost.ClassPostRepository;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassPostService {

    private final UserRepository userRepository;
    private final ClassPostRepository classPostRepository;
    private final S3Service s3Service;

    //학급 게시물 생성
    @Transactional
    public ResponseDto<String> createClassPost(PostRequestDto postRequestDto, List<MultipartFile> requestImages, User user) throws IOException {

        User userNow = userRepository.findById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );

        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        String group = userNow.getUserDetail().getGrade() + "-" + userNow.getUserDetail().getClassNum();
        ClassPost classPost = new ClassPost(title, content, group, userNow.getName());

        List<PostImage> images = new ArrayList<>();

        if (!requestImages.isEmpty()) {
            images = s3Service.uploadPostImages(requestImages, classPost);
        }

        classPost.setImage(images);
        classPost.setUser(userNow);
        userNow.addClassPost(classPost);
        classPostRepository.save(classPost);

        return ResponseDto.setSuccess(HttpStatus.OK, "학급 게시물 생성");

    }
}
