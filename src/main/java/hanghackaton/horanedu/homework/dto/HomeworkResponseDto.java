package hanghackaton.horanedu.homework.dto;

import hanghackaton.horanedu.homework.entity.Homework;
import lombok.Getter;

@Getter
public class HomeworkResponseDto {
    private String topic;
    private boolean checkBox;

    public HomeworkResponseDto(Homework homework) {
        this.topic = homework.getTopic();
        this.checkBox = homework.getCheckBox();
    }
}
