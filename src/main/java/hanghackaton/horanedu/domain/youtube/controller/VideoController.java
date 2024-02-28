package hanghackaton.horanedu.domain.youtube.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.security.UserDetailsImpl;
import hanghackaton.horanedu.domain.youtube.dto.PatchVideoTimeDto;
import hanghackaton.horanedu.domain.youtube.dto.PlayedVideoResponseDto;
import hanghackaton.horanedu.domain.youtube.dto.VideoResponseDto;
import hanghackaton.horanedu.domain.youtube.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/last")
    public ResponseDto<VideoResponseDto> getLastViewedVideo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return videoService.getLastViewedVideo(userDetails.getUser());
    }

    @GetMapping("/played")
    public ResponseDto<PlayedVideoResponseDto> getPlayedVideos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return videoService.getPlayedVideos(userDetails.getUser());
    }

    @PostMapping("/zzim/{id}")
    public ResponseDto<String> clickZzimBtn(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @PathVariable Integer id) {
        return videoService.clickZzimBtn(userDetails.getUser(), id);
    }

    @GetMapping("/zzim/list")
    public ResponseDto<List<String>> getZzimList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return videoService.getZzimList(userDetails.getUser());
    }
}
