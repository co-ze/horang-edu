package hanghackaton.horanedu.domain.user.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.common.jwt.JwtUtil;
import hanghackaton.horanedu.common.s3.S3Service;
import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.school.repository.SchoolRepository;
import hanghackaton.horanedu.domain.user.dto.authDto.LoginDto;
import hanghackaton.horanedu.domain.user.dto.authDto.SignupDto;
import hanghackaton.horanedu.domain.user.dto.requestDto.UserUpdateRequestDto;
import hanghackaton.horanedu.domain.user.dto.responseDto.PatchUserResponseDto;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import hanghackaton.horanedu.domain.user.entity.UserProgress;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import hanghackaton.horanedu.domain.user.repository.userDetail.UserDetailRepository;
import hanghackaton.horanedu.domain.user.repository.userProgress.UserProgressRepository;
import hanghackaton.horanedu.domain.user.userEnum.UserRole;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserProgressRepository userProgressRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final SchoolRepository schoolRepository;

    private static final String ADMIN_CODE = "horang";

    //회원가입
    @Transactional
    public ResponseDto<String> signup(SignupDto signupDto) {
        String email = signupDto.getEmail();
        String name = signupDto.getName();
        String password = passwordEncoder.encode(signupDto.getPassword());

        Optional<User> duplicateEmail = userRepository.findByEmail(email);
        if (duplicateEmail.isPresent()) {
            throw new GlobalException(ExceptionEnum.DUPLICATED_EMAIL);
        }

        UserRole userRole = UserRole.USER;
        if (signupDto.isAdmin()) {
            if (!StringUtils.pathEquals(ADMIN_CODE, signupDto.getAdminCode())) {
                throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
            }
            userRole = UserRole.ADMIN;
        }

        //유저 생성
        User user = new User(email, name, password, userRole);
        userRepository.saveAndFlush(user);
        //유저 상세 생성
        UserDetail userDetail = new UserDetail(user);
        userDetailRepository.saveAndFlush(userDetail);
        user.setUserDetail(userDetail);
        //유저 진행도 생성
        UserProgress userProgress = new UserProgress(user);
        userProgressRepository.saveAndFlush(userProgress);
        user.setUserProgress(userProgress);

        return ResponseDto.setSuccess(HttpStatus.OK,"회원 가입 성공!");
    }

    //로그인
    @Transactional
    public ResponseDto<String> login(LoginDto loginDto, HttpServletResponse response) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new GlobalException(ExceptionEnum.LOGIN_FORBIDDEN)
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new GlobalException(ExceptionEnum.LOGIN_FORBIDDEN);
        }

        String token = jwtUtil.createToken(user.getId(), user.getEmail(), user.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return ResponseDto.setSuccess(HttpStatus.OK, "로그인 성공~!");
    }

    @Transactional
    public ResponseDto<PatchUserResponseDto> updateUser(Long id,
                                                        MultipartFile image,
                                                        UserUpdateRequestDto userUpdateRequestDto,
                                                        User user) throws IOException {

        //사전 작업
        if (!Objects.equals(id, user.getId())) {
            throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
        }

        String imageUrl;

        if (image == null) {
            imageUrl = user.getUserDetail().getImage();
        } else {
            imageUrl = s3Service.uploadFile(image);
        }

        School school = schoolRepository.findSchoolByName(userUpdateRequestDto.getSchool()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_SCHOOL)
        );

        //변경
        UserDetail userDetail = user.getUserDetail();

        user.updateName(userUpdateRequestDto.getName());
        userDetail.setSchool(school);
        userDetail.updateImage(imageUrl);

        userRepository.saveAndFlush(user);
        userDetailRepository.saveAndFlush(userDetail);

        PatchUserResponseDto patchUserResponseDto = new PatchUserResponseDto(user, userDetail);
        return ResponseDto.setSuccess(HttpStatus.OK,"회원 정보 수정!", patchUserResponseDto);

    }

//    @Transactional(readOnly = true)
//    public ResponseDto<UserResponseDto> getStudent(Long id) {
//        UserDetail user = userDetailRepository.findUserById(id);
//        UserResponseDto studentResponseDto = new UserResponseDto(user);
//        return ResponseDto.setSuccess("학생 정보", studentResponseDto);
//    }

//    @Transactional(readOnly = true)
//    public ResponseDto<OneUserDto> getStudentRank(Long id) {
//        UserDetail user = userDetailRepository.findUserById(id);
//        OneUserDto studentRankDto = new OneUserDto(user);
//        return ResponseDto.setSuccess("내 순위 정보", studentRankDto);
//    }
//
//    @Transactional
//    public void updateRank(List<UserDetail> users) {
//        users.sort(Comparator.comparing(UserDetail::getExp).reversed());
//        for (int i = 0; i < users.size(); i++) {
//            UserDetail user = users.get(i);
//            user.updateRank(i + 1);
//            userDetailRepository.save(user);
//        }
//    }
//
//    @Transactional
//    public void updateSchoolRank(List<UserDetail> schoolUsers) {
//        schoolUsers.sort(Comparator.comparing(UserDetail::getExp).reversed());
//        for (int i = 0; i < schoolUsers.size(); i++) {
//            UserDetail user = schoolUsers.get(i);
//            user.updateSchoolRank(i + 1);
//            userDetailRepository.save(user);
//        }
//    }

}
