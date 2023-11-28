package hanghackaton.horanedu.domain.board.postEnum;

import lombok.Getter;

@Getter
public enum PostCategoryEnum {
    FREE(Category.FREE),
    QUESTION(Category.QUESTION),
    DIARY(Category.DIARY);

    public final String category;

    PostCategoryEnum(String category) {
        this.category = category;
    }

    public static class Category {
        public static final String FREE = "FREE";
        public static final String QUESTION = "QUESTION";
        public static final String DIARY = "DIARY";
    }

}
