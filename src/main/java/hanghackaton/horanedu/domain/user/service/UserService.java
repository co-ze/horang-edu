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
import hanghackaton.horanedu.domain.user.dto.requestDto.UserDepartmentDto;
import hanghackaton.horanedu.domain.user.dto.requestDto.UserUpdateRequestDto;
import hanghackaton.horanedu.domain.user.dto.responseDto.PatchUserResponseDto;
import hanghackaton.horanedu.domain.user.dto.responseDto.UserResponseDto;
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

        //유저 계정
        if (Objects.equals(userRole, UserRole.USER)) {
            //유저 상세 생성
            UserDetail userDetail = new UserDetail(user);
            userDetailRepository.saveAndFlush(userDetail);
            user.setUserDetail(userDetail);
            //유저 진행도 생성
            UserProgress userProgress = new UserProgress(user);
            userProgressRepository.saveAndFlush(userProgress);
        } else { //관리자 계정
            //유저 상세 생성
            UserDetail userDetail = new UserDetail(user);
            userDetailRepository.saveAndFlush(userDetail);
            user.setUserDetail(userDetail);
        }

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
        school.getUserDetailList().add(userDetail);
        userDetail.updateImage(imageUrl);

        schoolRepository.saveAndFlush(school);
        userRepository.saveAndFlush(user);
        userDetailRepository.saveAndFlush(userDetail);

        PatchUserResponseDto patchUserResponseDto = new PatchUserResponseDto(user, userDetail);
        return ResponseDto.setSuccess(HttpStatus.OK,"회원 정보 수정!", patchUserResponseDto);

    }

    @Transactional(readOnly = true)
    public ResponseDto<UserResponseDto> getUser(Long id, User user) {

        if (!Objects.equals(id, user.getId())) {
            throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
        }

        Optional<User> checkUser = userRepository.findById(id);
        if (checkUser.isEmpty()) {
            throw new GlobalException(ExceptionEnum.NOT_FOUND_USER);
        }

        UserResponseDto userResponseDto = new UserResponseDto(
                user.getEmail(),
                user.getName(),
                user.getUserDetail().getSchool(),
                user.getRole(),
                user.getUserDetail().getImage()
        );

        return ResponseDto.set(HttpStatus.OK, "회원 정보 조회", userResponseDto);

    }

    @Transactional
    public ResponseDto<String> updateUserDepartment(Long id, UserDepartmentDto userDepartmentDto, User user) {

        if (!Objects.equals(id, user.getId())) {
            throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
        }
        UserDetail userDetail = userDetailRepository.findUserDetailByUser(user);

        String teacherEmail = userDepartmentDto.getEmail();
        User teacher;
        if (!Objects.equals(teacherEmail, "")) {
            teacher = userRepository.findUserByEmail(teacherEmail);
            if (teacher == null) {
                throw new GlobalException(ExceptionEnum.NOT_FOUND_TEACHER);
            }
            userDetail.setTeacherName(teacher.getName());
            userDetail.updateDepartment(teacher.getUserDetail().getGrade(), teacher.getUserDetail().getClassNum());
            userDetailRepository.save(userDetail);
            return ResponseDto.setSuccess(HttpStatus.OK, "선생님 등록 완료!");
        } else {
            userDetail.updateDepartment(userDepartmentDto.getGrade(), userDepartmentDto.getGroup());
            userDetailRepository.save(userDetail);
            return ResponseDto.setSuccess(HttpStatus.OK, "학년 / 반 입력 성공!");
        }

    }

}
