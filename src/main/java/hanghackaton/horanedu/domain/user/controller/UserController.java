package hanghackaton.horanedu.domain.user.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.security.UserDetailsImpl;
import hanghackaton.horanedu.domain.user.dto.authDto.LoginDto;
import hanghackaton.horanedu.domain.user.dto.authDto.SignupDto;
import hanghackaton.horanedu.domain.user.dto.UserResponseDto;
import hanghackaton.horanedu.domain.user.dto.requestDto.UserUpdateRequestDto;
import hanghackaton.horanedu.domain.user.dto.responseDto.PatchUserResponseDto;
import hanghackaton.horanedu.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<String> signup(@Valid @RequestBody SignupDto signupDto) {
        return userService.signup(signupDto);
    }

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        return userService.login(loginDto, response);
    }

    @PatchMapping(value = "/{id}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseDto<PatchUserResponseDto> updateUserDetail(@PathVariable Long id,
                                                              @RequestPart(name = "image", required = false)
                                                              MultipartFile image,
                                                              @RequestPart(name = "userUpdateRequestDto")
                                                              UserUpdateRequestDto userUpdateRequestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return userService.updateUser(id, image, userUpdateRequestDto, userDetails.getUser());
    }

    //    @GetMapping("/{id}")
//    public ResponseDto<UserResponseDto> getUser(@PathVariable Long id) {
//        return userService.getStudent(id);
//    }

    //    @GetMapping("/rank/{id}")
//    public ResponseDto<OneUserDto> getUserRank(@PathVariable Long id) {
//        return userService.getStudentRank(id);
//    }
//}
}


