package hanghackaton.horanedu.domain.youtube.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import hanghackaton.horanedu.domain.youtube.dto.PatchVideoTimeDto;
import hanghackaton.horanedu.domain.youtube.dto.PlayedVideoResponseDto;
import hanghackaton.horanedu.domain.youtube.dto.VideoResponseDto;
import hanghackaton.horanedu.domain.youtube.entity.Video;
import hanghackaton.horanedu.domain.youtube.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<String> patchVideoTime(User user, PatchVideoTimeDto patchVideoTimeDto) {

        log.info("-----PATCH VIDEO PLAY TIME START-----");

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //유저 id로 video 찾기
        Video video = videoRepository.findByUserId(userNow.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_VIDEO)
        );
        Long videoId = patchVideoTimeDto.getId();
        Integer playTime = patchVideoTimeDto.getPlayTime();
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
        video.setLastViewed(videoId);

        videoRepository.save(video);

        log.info("-----PATCH VIDEO PLAY TIME END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "재생시간 수정");

    }

    @Transactional(readOnly = true)
    public ResponseDto<VideoResponseDto> getVideoTime(User user, Long id) {

        log.info("-----READ VIDEO PLAY TIME START-----");

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //유저 id로 video 찾기
        Video video = videoRepository.findByUserId(userNow.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_VIDEO)
        );

        Integer playTime = getVideo(video, id);

        VideoResponseDto videoResponseDto = new VideoResponseDto(id, playTime);

        log.info("-----READ VIDEO PLAY TIME END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "영상 재생시간 조회", videoResponseDto);

    }

    @Transactional(readOnly = true)
    public ResponseDto<VideoResponseDto> getLastViewedVideo(User user) {

        log.info("-----READ LAST VIEWED VIDEO START-----");

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //유저 id로 video 찾기
        Video video = videoRepository.findByUserId(userNow.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_VIDEO)
        );

        Long videoId = video.getLastViewed();

        Integer playTime = getVideo(video, videoId);

        VideoResponseDto videoResponseDto = new VideoResponseDto(videoId, playTime);

        log.info("-----READ LAST VIEWED VIDEO END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "마지막 학습 영상", videoResponseDto);

    }

    @Transactional(readOnly = true)
    public ResponseDto<PlayedVideoResponseDto> getPlayedVideos(User user) {

        log.info("-----READ PLAYED VIDEO LIST START-----");

        //현재 로그인 된 유저가 존재 하는지 확인
        User userNow = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        //유저 id로 video 찾기
        Video video = videoRepository.findByUserId(userNow.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_VIDEO)
        );

        Map<Long, Integer> playedMap = new LinkedHashMap<>();
        for (long i = 1L; i <= 16L; i++) {
            Integer playTime = getVideo(video, i);
            if (!Objects.equals(playTime, 0)) {
                playedMap.put(i, playTime);
            }
        }

        PlayedVideoResponseDto playedVideoResponseDto = new PlayedVideoResponseDto(playedMap);

        log.info("-----READ PLAYED VIDEO LIST END-----");

        return ResponseDto.setSuccess(HttpStatus.OK, "학습 중인 강의", playedVideoResponseDto);
    }

    @Transactional
    public ResponseDto<String> clickZzimBtn(User user, Integer videoId) {

        log.info("-----CLICK ZZIM BTN START-----");
        //유저 id로 video 찾기
        Video video = videoRepository.findByUserId(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_VIDEO)
        );

        if (videoId == 1) {
            if(!video.getZzim1()) {
                video.setZzim1(true);
            }else{
                video.setZzim1(false);
            }
        } else if (videoId == 2) {
            if(!video.getZzim2()) {
                video.setZzim2(true);
            }else{
                video.setZzim2(false);
            }
        } else if (videoId == 3) {
            if(!video.getZzim3()) {
                video.setZzim3(true);
            }else{
                video.setZzim3(false);
            }
        } else if (videoId == 4) {
            if(!video.getZzim4()) {
                video.setZzim4(true);
            }else{
                video.setZzim4(false);
            }
        } else if (videoId == 5) {
            if(!video.getZzim5()) {
                video.setZzim5(true);
            }else{
                video.setZzim5(false);
            }
        } else if (videoId == 6) {
            if(!video.getZzim6()) {
                video.setZzim6(true);
            }else{
                video.setZzim6(false);
            }
        } else if (videoId == 7) {
            if(!video.getZzim7()) {
                video.setZzim7(true);
            }else{
                video.setZzim7(false);
            }
        } else if (videoId == 8) {
            if(!video.getZzim8()) {
                video.setZzim8(true);
            }else{
                video.setZzim8(false);
            }
        } else if (videoId == 9) {
            if(!video.getZzim9()) {
                video.setZzim9(true);
            }else{
                video.setZzim9(false);
            }
        } else if (videoId == 10) {
            if(!video.getZzim10()) {
                video.setZzim10(true);
            }else{
                video.setZzim10(false);
            }
        } else if (videoId == 11) {
            if(!video.getZzim11()) {
                video.setZzim11(true);
            }else{
                video.setZzim11(false);
            }
        } else if (videoId == 12) {
            if(!video.getZzim12()) {
                video.setZzim12(true);
            }else{
                video.setZzim12(false);
            }
        } else if (videoId == 13) {
            if(!video.getZzim13()) {
                video.setZzim13(true);
            }else{
                video.setZzim13(false);
            }
        } else if (videoId == 14) {
            if(!video.getZzim14()) {
                video.setZzim14(true);
            }else{
                video.setZzim14(false);
            }
        } else if (videoId == 15) {
            if(!video.getZzim15()) {
                video.setZzim15(true);
            }else{
                video.setZzim15(false);
            }
        } else if (videoId == 16) {
            if(!video.getZzim16()) {
                video.setZzim16(true);
            }else{
                video.setZzim16(false);
            }
        }

        return ResponseDto.setSuccess(HttpStatus.OK, "찜 등록 완료!");
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<String>> getZzimList(User user) {

        log.info("-----GET ZZIM LIST START-----");
        //유저 id로 video 찾기
        Video video = videoRepository.findByUserId(user.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_VIDEO)
        );

        List<String> getList = new ArrayList<>();
        if(video.getZzim1()) {
            getList.add("1");
        }else if(video.getZzim2()){
            getList.add("2");
        }else if(video.getZzim3()){
            getList.add("3");
        }else if(video.getZzim4()){
            getList.add("4");
        }else if(video.getZzim5()){
            getList.add("5");
        }else if(video.getZzim6()){
            getList.add("6");
        }else if(video.getZzim7()){
            getList.add("7");
        }else if(video.getZzim8()){
            getList.add("8");
        }else if(video.getZzim9()){
            getList.add("9");
        }else if(video.getZzim10()){
            getList.add("10");
        }else if(video.getZzim11()){
            getList.add("11");
        }else if(video.getZzim12()){
            getList.add("12");
        }else if(video.getZzim13()){
            getList.add("13");
        }else if(video.getZzim14()){
            getList.add("14");
        }else if(video.getZzim15()){
            getList.add("15");
        }else if(video.getZzim16()){
            getList.add("16");
        }

        return ResponseDto.setSuccess(HttpStatus.OK, "찜 목록 조회 완료.", getList);

    }

    public Integer getVideo(Video video, Long videoId) {

        Integer playTime = null;
        if (videoId == 1) {
            playTime = video.getVideo1();
        } else if (videoId == 2) {
            playTime = video.getVideo2();
        } else if (videoId == 3) {
            playTime = video.getVideo3();
        } else if (videoId == 4) {
            playTime = video.getVideo4();
        } else if (videoId == 5) {
            playTime = video.getVideo5();
        } else if (videoId == 6) {
            playTime = video.getVideo6();
        } else if (videoId == 7) {
            playTime = video.getVideo7();
        } else if (videoId == 8) {
            playTime = video.getVideo8();
        } else if (videoId == 9) {
            playTime = video.getVideo9();
        } else if (videoId == 10) {
            playTime = video.getVideo10();
        } else if (videoId == 11) {
            playTime = video.getVideo11();
        } else if (videoId == 12) {
            playTime = video.getVideo12();
        } else if (videoId == 13) {
            playTime = video.getVideo13();
        } else if (videoId == 14) {
            playTime = video.getVideo14();
        } else if (videoId == 15) {
            playTime = video.getVideo15();
        } else if (videoId == 16) {
            playTime = video.getVideo16();
        }
        return playTime;

    }

}
