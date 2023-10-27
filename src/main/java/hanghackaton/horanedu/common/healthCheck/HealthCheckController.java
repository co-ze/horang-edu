package hanghackaton.horanedu.common.healthCheck;

import hanghackaton.horanedu.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {

    @GetMapping
    public ResponseDto<String> healthCheck() {
        return ResponseDto.setSuccess(HttpStatus.OK, "성공");
    }
}
