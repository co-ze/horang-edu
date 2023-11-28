package hanghackaton.horanedu.domain.youtube.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.security.UserDetailsImpl;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.youtube.dto.PatchVideoTimeDto;
import hanghackaton.horanedu.domain.youtube.dto.VideoResponseDto;
import hanghackaton.horanedu.domain.youtube.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video")
public class VideoController {

    private final VideoService videoService;

    @PatchMapping()
    public ResponseDto<String> patchVideoTime(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @RequestBody PatchVideoTimeDto patchVideoTimeDto) {
        return videoService.patchVideoTime(userDetails.getUser(), patchVideoTimeDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<VideoResponseDto> getVideoTime(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable Long id) {
        return videoService.getVideoTime(userDetails.getUser(), id);
    }
}
