package hanghackaton.horanedu.user.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.user.dto.UserRequestDto;
import hanghackaton.horanedu.user.entity.User;
import hanghackaton.horanedu.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<String> createUser(UserRequestDto userRequestDto) {
        User user = new User(userRequestDto);
        userRepository.saveAndFlush(user);
        return ResponseDto.setSuccess("아이디 생성");
    }

}
