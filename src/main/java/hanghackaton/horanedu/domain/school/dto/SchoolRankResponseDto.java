package hanghackaton.horanedu.domain.school.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SchoolRankResponseDto {
    private String userSchoolName;
    private int userSchoolRank;
    private int userSchoolExp;
    private List<SchoolRankDto> schoolRankDtoList;

    public SchoolRankResponseDto(String name, int rank, int exp, List<SchoolRankDto> userRankDtoList) {
        this.userSchoolName = name;
        this.userSchoolRank = rank;
        this.userSchoolExp = exp;
        this.schoolRankDtoList = userRankDtoList;
    }
}
