package hanghackaton.horanedu.rank.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.rank.service.RankService;
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
    public ResponseDto<Page<StudentRankDto>> getPersonalRank(@RequestParam(name = "page") int page,
                                                             @RequestParam(name = "size") int size) {
        return rankService.getPersonalRank(page - 1, size);
    }

}
