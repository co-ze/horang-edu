//package hanghackaton.horanedu.domain.dibs.controller;
//
//import hanghackaton.horanedu.common.dto.ResponseDto;
//import hanghackaton.horanedu.domain.dibs.dto.DibsRequestDto;
//import hanghackaton.horanedu.domain.dibs.dto.DibsResponseDto;
//import hanghackaton.horanedu.domain.dibs.service.DibsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/dibs")
//public class DibsController {
//    private final DibsService dibsService;
//
//    @PostMapping
//    public ResponseDto<String> createDibs(@RequestBody DibsRequestDto dibsRequestDto) {
//        return dibsService.createDibs(dibsRequestDto);
//    }
//
//    @GetMapping
//    public ResponseDto<List<DibsResponseDto>> getDibsList() {
//        return dibsService.getDibsList();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseDto<String> deleteDibs(@PathVariable Long id) {
//        return dibsService.deleteDibs(id);
//    }
//}
