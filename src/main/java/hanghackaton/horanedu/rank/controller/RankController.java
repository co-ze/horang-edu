package hanghackaton.horanedu.rank.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.rank.service.RankService;
import hanghackaton.horanedu.school.dto.SchoolRankDto;
import hanghackaton.horanedu.user.dto.StudentRankDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rank")
public class RankController {

    private final RankService rankService;

    @GetMapping
    public ResponseDto<Page<StudentRankDto>> getPersonalRankList(@RequestParam(name = "page") int page,
                                                                 @RequestParam(name = "size") int size) {
        return rankService.getPersonalRankList(page - 1, size);
    }

    @GetMapping("/school")
    public ResponseDto<Page<SchoolRankDto>> getSchoolRankList(@RequestParam(name = "page") int page,
                                                              @RequestParam(name = "size") int size) {
        return rankService.getSchoolRankList(page - 1, size);
    }

    @GetMapping("/in-school")
    public ResponseDto<Page<StudentRankDto>> getPersonalRankInSchoolList(@RequestParam(name = "school") String school,
                                                                         @RequestParam(name = "page") int page,
                                                                         @RequestParam(name = "size") int size) {
        return rankService.getPersonalRankInSchoolList(school, page - 1, size);
    }

}
