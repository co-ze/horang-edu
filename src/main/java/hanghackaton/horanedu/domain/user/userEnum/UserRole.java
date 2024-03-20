package hanghackaton.horanedu.domain.user.userEnum;

import lombok.Getter;

@Getter
public enum UserRole {
    STUDENT(Authority.STUDENT),  // 사용자 권한
    TEACHER(Authority.TEACHER);  // 관리자 권한

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String STUDENT = "ROLE_STUDENT";
        public static final String TEACHER = "ROLE_TEACHER";
    }
}
