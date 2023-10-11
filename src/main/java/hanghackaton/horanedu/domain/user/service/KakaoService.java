package hanghackaton.horanedu.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.jwt.JwtUtil;
import hanghackaton.horanedu.domain.user.dto.authDto.KakaoUserDto;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import hanghackaton.horanedu.domain.user.entity.UserProgress;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import hanghackaton.horanedu.domain.user.repository.userDetail.UserDetailRepository;
import hanghackaton.horanedu.domain.user.repository.userProgress.UserProgressRepository;
import hanghackaton.horanedu.domain.user.userEnum.UserRole;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserProgressRepository userProgressRepository;
    private final JwtUtil jwtUtil;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    public ResponseDto<String> loginWithKakao(String code, HttpServletResponse response) throws JsonProcessingException {
        //프론트에서 로그인 요청 시에 받는 '인가 코드' 이용해 토큰 요청
        String accessToken = requestToken(code);

        //"액세스 토큰"으로 "카카오 정보" 가져오기
        KakaoUserDto userInfo = requestUserInfo(accessToken);

        //계정이 있으면 로그인, 없으면 회원 가입
        User user = signupOrLogin(userInfo);

        //로그인 된 유저 정보로 토큰 생성 후 반환
        String token = jwtUtil.createToken(user.getId(), user.getEmail(), user.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return ResponseDto.setSuccess(HttpStatus.OK, "카카오 로그인 성공!", user.getName());
    }



    private String requestToken(String code) throws JsonProcessingException {
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("client_secret", kakaoClientSecret);
        body.add("redirect_uri", kakaoRedirectUri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, httpHeaders);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();

    }

    private KakaoUserDto requestUserInfo(String accessToken) throws JsonProcessingException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> userInfo = new HttpEntity<>(httpHeaders);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                userInfo,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        String profileImage = jsonNode.get("properties")
                .get("profile_image").asText();
        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email + ", " + profileImage);

        return new KakaoUserDto(id, email, nickname, profileImage);

    }

    private User signupOrLogin(KakaoUserDto userInfo) {

        User user = userRepository.findByKakaoId(userInfo.getId());

        //카카오 로그인을 처음 시도한 유저
        if (user == null) {
            //그 중 이메일이 중복인 유저
            User duplicateEmailUser = userRepository.findUserByEmail(userInfo.getEmail());
            if (duplicateEmailUser != null) {
                duplicateEmailUser.setKakaoId(userInfo.getId());
                userRepository.save(duplicateEmailUser);
                return duplicateEmailUser;
                //kakaoId만 추가 후 반환
            } else {
                Long kakaoId = userInfo.getId();
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);
                String name = userInfo.getName();
                String email = userInfo.getEmail();

                //유저 생성
                User newKakaoUser = new User(email, name, encodedPassword, UserRole.USER, kakaoId);
                userRepository.saveAndFlush(newKakaoUser);
                //유저 상세 생성
                UserDetail userDetail = new UserDetail(newKakaoUser);
                userDetailRepository.saveAndFlush(userDetail);
                newKakaoUser.setUserDetail(userDetail);
                //유저 진행도 생성
                UserProgress userProgress = new UserProgress(newKakaoUser);
                userProgressRepository.saveAndFlush(userProgress);
                newKakaoUser.setUserProgress(userProgress);
                return newKakaoUser;
            }
        }
        return user;
    }

}

/*
https://kauth.kakao.com/oauth/authorize
?client_id=f524b4a31a8276b6dbd87d8459160c49
&redirect_uri=http://localhost:8080/api/user/kakao
&response_type=code
 */
