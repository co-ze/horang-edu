//package hanghackaton.horanedu.domain.dibs.service;
//
//import hanghackaton.horanedu.common.dto.ResponseDto;
//import hanghackaton.horanedu.domain.dibs.dto.DibsRequestDto;
//import hanghackaton.horanedu.domain.dibs.dto.DibsResponseDto;
//import hanghackaton.horanedu.domain.dibs.entity.Dibs;
//import hanghackaton.horanedu.domain.dibs.repository.DibsRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class DibsService {
//    private final DibsRepository dibsRepository;
//
//    @Transactional
//    public ResponseDto<String> createDibs(DibsRequestDto dibsRequestDto) {
//        Dibs dibs = new Dibs(dibsRequestDto);
//        dibsRepository.save(dibs);
//        return ResponseDto.setSuccess("찜!");
//    }
//
//    @Transactional(readOnly = true)
//    public ResponseDto<List<DibsResponseDto>> getDibsList() {
//        List<DibsResponseDto> dibsList = dibsRepository.findAll()
//                .stream()
//                .map(DibsResponseDto::new)
//                .toList();
//        return ResponseDto.setSuccess("찜 목록", dibsList);
//    }
//
//    @Transactional
//    public ResponseDto<String> deleteDibs(Long id) {
//        dibsRepository.deleteById(id);
//        return ResponseDto.setSuccess("찜 삭제");
//    }
//}
