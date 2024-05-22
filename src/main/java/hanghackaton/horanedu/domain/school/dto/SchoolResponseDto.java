package hanghackaton.horanedu.domain.school.dto;

import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import lombok.Getter;

import java.util.List;

@Getter
public class SchoolResponseDto {
    private Long id;
    private String grade;
    private String name;
    private String teacherName;
    private List<UserListDto> userList;
    private String groupCode;

    public SchoolResponseDto(School school, String teacherName, List<UserListDto> userList) {
        this.id = school.getId();
        this.grade = school.getGrade();
        this.name = school.getName();
        this.teacherName = teacherName;
        this.userList = userList;
        this.groupCode = school.getGroupCode();
    }
}
