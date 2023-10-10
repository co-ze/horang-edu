//package hanghackaton.horanedu.domain.homework.service;
//
//import hanghackaton.horanedu.common.dto.ResponseDto;
//import hanghackaton.horanedu.domain.homework.dto.HomeworkRequestDto;
//import hanghackaton.horanedu.domain.homework.dto.HomeworkResponseDto;
//import hanghackaton.horanedu.domain.homework.entity.Homework;
//import hanghackaton.horanedu.domain.homework.repository.HomeworkRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class HomeworkService {
//    private final HomeworkRepository homeworkRepository;
//
//    @Transactional
//    public ResponseDto<String> createHomework(HomeworkRequestDto homeworkRequestDto) {
//        Homework homework = new Homework(homeworkRequestDto);
//        homeworkRepository.save(homework);
//        return ResponseDto.setSuccess("과제 생성");
//    }
//
//    @Transactional
//    public ResponseDto<String> checkHomework(Long id) {
//        Homework homework = homeworkRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("해당 id 과제가 없습니다..")
//        );
//        if (!homework.getCheckBox()) {
//            homework.checkOn();
//        } else {
//            homework.checkOff();
//        }
//        homeworkRepository.save(homework);
//
//        return ResponseDto.setSuccess("체크!");
//    }
//
//    @Transactional(readOnly = true)
//    public ResponseDto<List<HomeworkResponseDto>> getHomeworkList() {
//        List<HomeworkResponseDto> homeworkList = homeworkRepository.findAll()
//                .stream()
//                .map(HomeworkResponseDto::new)
//                .toList();
//        return ResponseDto.setSuccess("오늘의 과제", homeworkList);
//    }
//}
