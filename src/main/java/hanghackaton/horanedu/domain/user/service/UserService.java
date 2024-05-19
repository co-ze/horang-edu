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
import hanghackaton.horanedu.domain.user.dto.authDto.TempSignupDto;
import hanghackaton.horanedu.domain.user.dto.requestDto.UserDepartmentDto;
import hanghackaton.horanedu.domain.user.dto.requestDto.UserUpdateRequestDto;
import hanghackaton.horanedu.domain.user.dto.responseDto.PatchUserResponseDto;
import hanghackaton.horanedu.domain.user.dto.responseDto.TempSignupResponseDto;
import hanghackaton.horanedu.domain.user.dto.responseDto.UserResponseDto;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import hanghackaton.horanedu.domain.user.entity.UserProgress;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import hanghackaton.horanedu.domain.user.repository.userDetail.UserDetailRepository;
import hanghackaton.horanedu.domain.user.repository.userProgress.UserProgressRepository;
import hanghackaton.horanedu.domain.user.userEnum.UserRole;
import hanghackaton.horanedu.domain.youtube.entity.Video;
import hanghackaton.horanedu.domain.youtube.repository.VideoRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserProgressRepository userProgressRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final SchoolRepository schoolRepository;
    private final VideoRepository videoRepository;

    //회원가입
    @Transactional
    public ResponseDto<String> signup(SignupDto signupDto) {
        log.info("-----회원가입 시작-----");

        String email = signupDto.getEmail();
        String name = signupDto.getName();
        String password = passwordEncoder.encode(signupDto.getPassword());

        Optional<User> duplicateEmail = userRepository.findByEmail(email);
        if (duplicateEmail.isPresent()) {
            throw new GlobalException(ExceptionEnum.DUPLICATED_EMAIL);
        }

        UserRole userRole = UserRole.STUDENT;
        if (!signupDto.isRole()) {
            userRole = UserRole.TEACHER;
        }

        //유저 생성
        User user = new User(email, name, password, userRole);
        userRepository.saveAndFlush(user);

        //유저 계정
        if (Objects.equals(userRole, UserRole.STUDENT)) {
            //유저 상세 생성
            UserDetail userDetail = new UserDetail(user);
            userDetailRepository.saveAndFlush(userDetail);
            user.setUserDetail(userDetail);
            //유저 진행도 생성
            UserProgress userProgress = new UserProgress(user);
            userProgressRepository.saveAndFlush(userProgress);
            //유저별 비디오 재생 시간 생성
            Video video = new Video(user);
            videoRepository.saveAndFlush(video);
        } else { //관리자 계정
            //유저 상세 생성
            UserDetail userDetail = new UserDetail(user);
            userDetailRepository.saveAndFlush(userDetail);
            user.setUserDetail(userDetail);
        }

        log.info("-----회원가입 종료-----");

        return ResponseDto.setSuccess(HttpStatus.OK,"회원 가입 성공!");
    }

    //로그인
    @Transactional
    public ResponseDto<String> login(LoginDto loginDto, HttpServletResponse response) {

        log.info("-----LOGIN START-----");

        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new GlobalException(ExceptionEnum.LOGIN_FORBIDDEN)
        );

        log.info(String.valueOf(user));
        log.info(user.getPassword());

        if (user.getPassword().length() != 6) {
            log.info("-----user.getPassword().length() != 6-----");
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new GlobalException(ExceptionEnum.LOGIN_FORBIDDEN);
            }
        }else{
            log.info("-----else-----");
            if (!StringUtils.pathEquals(password, user.getPassword())) {
                throw new GlobalException(ExceptionEnum.LOGIN_FORBIDDEN);
            }
        }

        String token = jwtUtil.createToken(user.getId(), user.getEmail(), user.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        log.info("-----LOGIN END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "로그인 성공~!");
    }

    @Transactional
    public ResponseDto<PatchUserResponseDto> updateUser(Long id,
                                                        MultipartFile image,
                                                        UserUpdateRequestDto userUpdateRequestDto,
                                                        User user) throws IOException {

        log.info("-----USER UPDATE START-----");

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

        //변경
        UserDetail userDetail = user.getUserDetail();

        user.updateName(userUpdateRequestDto.getName());
        userDetail.updateImage(imageUrl);
        userRepository.saveAndFlush(user);
        userDetailRepository.saveAndFlush(userDetail);

        PatchUserResponseDto patchUserResponseDto = new PatchUserResponseDto(user, userDetail);

        log.info("-----USER UPDATE END-----");

        return ResponseDto.setSuccess(HttpStatus.OK,"회원 정보 수정!", patchUserResponseDto);

    }

    @Transactional(readOnly = true)
    public ResponseDto<UserResponseDto> getUser(User user) {

        log.info("-----READ USER START-----");

        Optional<User> checkUser = userRepository.findById(user.getId());
        if (checkUser.isEmpty()) {
            throw new GlobalException(ExceptionEnum.NOT_FOUND_USER);
        }

        UserProgress userProgress = userProgressRepository.findUserProgressByUser(user);
        if (userProgress == null) throw new GlobalException(ExceptionEnum.NOT_FOUND_USER);

        String schoolName;
        if(user.getUserDetail().getSchool() == null){
            schoolName = "없음";
        }else{
            schoolName = user.getUserDetail().getSchool().getName();
        }

        UserResponseDto userResponseDto = new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                schoolName,
                user.getRole(),
                user.getUserDetail().getImage(),
                userProgress.getLevel(),
                userProgress.getChapter(),
                userProgress.getExp()
        );

        log.info("-----READ USER END-----");

        return ResponseDto.set(HttpStatus.OK, "회원 정보 조회", userResponseDto);

    }

    @Transactional
    public ResponseDto<String> updateUserDepartment(UserDepartmentDto userDepartmentDto, User user) {

        log.info("-----UPDATE USER DETAIL START-----");
        UserDetail userDetail = userDetailRepository.findUserDetailByUser(user);

        String groupCode = userDepartmentDto.getGroupCode();
        School group = schoolRepository.findSchoolByGroupCode(groupCode);
        if(group == null) {
            throw new GlobalException(ExceptionEnum.NOT_FOUND_SCHOOL);
        }

        userDetail.setSchool(group);
        group.getUserDetailList().add(userDetail);
        schoolRepository.saveAndFlush(group);
        userDetailRepository.save(userDetail);

        log.info("-----UPDATE USER DETAIL END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "그룹 가입 완료!");


    }

    @Transactional
    public ResponseDto<TempSignupResponseDto> createTempUser(TempSignupDto tempSignupDto, User loginUser) {
        log.info("-----임시 회원 생성 시작-----");
        /*변수 할당*/
        School group = schoolRepository.findById(tempSignupDto.getGroupId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_SCHOOL)
        );
        String tempName = tempSignupDto.getStudentName();
        Optional<User> duplicateEmail = userRepository.findByEmail(tempName);
        if (duplicateEmail.isPresent()) {
            throw new GlobalException(ExceptionEnum.DUPLICATED_EMAIL);
        }

        User teacher = userRepository.findUserById(loginUser.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<6; i++) {
            sb.append((char)(random.nextInt(26)+65));
        }

        String password = sb.toString();

        //유저 생성
        User user = new User(tempName, tempName, password, UserRole.STUDENT);
        userRepository.saveAndFlush(user);

        //유저 상세 생성
        UserDetail userDetail = new UserDetail(user);
        userDetail.setSchool(group);
        userDetailRepository.saveAndFlush(userDetail);
        user.setUserDetail(userDetail);
        //유저 진행도 생성
        UserProgress userProgress = new UserProgress(user);
        userProgressRepository.saveAndFlush(userProgress);
        //유저별 비디오 재생 시간 생성
        Video video = new Video(user);
        videoRepository.saveAndFlush(video);

        TempSignupResponseDto responseDto = new TempSignupResponseDto(tempName, password);
        log.info("-----임시 회원 생성 종료-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "임시 계정 생성", responseDto);
    }

}
