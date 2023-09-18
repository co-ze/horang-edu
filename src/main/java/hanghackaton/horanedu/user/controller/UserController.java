package hanghackaton.horanedu.user.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.user.dto.UserRequestDto;
import hanghackaton.horanedu.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/make")
    public ResponseDto<String> createUser(UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }
}
