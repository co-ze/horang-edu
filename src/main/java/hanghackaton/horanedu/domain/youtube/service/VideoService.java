package hanghackaton.horanedu.domain.youtube.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import hanghackaton.horanedu.domain.youtube.dto.PatchVideoTimeDto;
import hanghackaton.horanedu.domain.youtube.dto.VideoResponseDto;
import hanghackaton.horanedu.domain.youtube.entity.Video;
import hanghackaton.horanedu.domain.youtube.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<String> patchVideoTime(User user, PatchVideoTimeDto patchVideoTimeDto) {

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //유저 id로 video 찾기
        Video video = videoRepository.findByUserId(userNow.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_VIDEO)
        );
        Long videoId = patchVideoTimeDto.getId();
        String playTime = patchVideoTimeDto.getPlayTime();
        if (videoId == 1) {
            video.setVideo1(playTime);
        } else if (videoId == 2) {
            video.setVideo2(playTime);
        } else if (videoId == 3) {
            video.setVideo3(playTime);
        } else if (videoId == 4) {
            video.setVideo4(playTime);
        } else if (videoId == 5) {
            video.setVideo5(playTime);
        } else if (videoId == 6) {
            video.setVideo6(playTime);
        } else if (videoId == 7) {
            video.setVideo7(playTime);
        } else if (videoId == 8) {
            video.setVideo8(playTime);
        } else if (videoId == 9) {
            video.setVideo9(playTime);
        } else if (videoId == 10) {
            video.setVideo10(playTime);
        } else if (videoId == 11) {
            video.setVideo11(playTime);
        } else if (videoId == 12) {
            video.setVideo12(playTime);
        } else if (videoId == 13) {
            video.setVideo13(playTime);
        } else if (videoId == 14) {
            video.setVideo14(playTime);
        } else if (videoId == 15) {
            video.setVideo15(playTime);
        } else if (videoId == 16) {
            video.setVideo16(playTime);
        }

        videoRepository.save(video);

        return ResponseDto.setSuccess(HttpStatus.OK, "재생시간 수정");

    }

    @Transactional(readOnly = true)
    public ResponseDto<VideoResponseDto> getVideoTime(User user, Long id) {

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //유저 id로 video 찾기
        Video video = videoRepository.findByUserId(userNow.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_VIDEO)
        );

        String playTime = null;

        if (id == 1) {
            playTime = video.getVideo1();
        } else if (id == 2) {
            playTime = video.getVideo2();
        } else if (id == 3) {
            playTime = video.getVideo3();
        } else if (id == 4) {
            playTime = video.getVideo4();
        } else if (id == 5) {
            playTime = video.getVideo5();
        } else if (id == 6) {
            playTime = video.getVideo6();
        } else if (id == 7) {
            playTime = video.getVideo7();
        } else if (id == 8) {
            playTime = video.getVideo8();
        } else if (id == 9) {
            playTime = video.getVideo9();
        } else if (id == 10) {
            playTime = video.getVideo10();
        } else if (id == 11) {
            playTime = video.getVideo11();
        } else if (id == 12) {
            playTime = video.getVideo12();
        } else if (id == 13) {
            playTime = video.getVideo13();
        } else if (id == 14) {
            playTime = video.getVideo14();
        } else if (id == 15) {
            playTime = video.getVideo15();
        } else if (id == 16) {
            playTime = video.getVideo16();
        }

        VideoResponseDto videoResponseDto = new VideoResponseDto(id, playTime);

        return ResponseDto.setSuccess(HttpStatus.OK, "영상 재생시간 조회", videoResponseDto);

    }
}
