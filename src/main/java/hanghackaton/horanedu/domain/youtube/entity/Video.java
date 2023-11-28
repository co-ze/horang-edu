package hanghackaton.horanedu.domain.youtube.entity;

import hanghackaton.horanedu.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String video1;
    @Column
    private String video2;
    @Column
    private String video3;
    @Column
    private String video4;
    @Column
    private String video5;
    @Column
    private String video6;
    @Column
    private String video7;
    @Column
    private String video8;
    @Column
    private String video9;
    @Column
    private String video10;
    @Column
    private String video11;
    @Column
    private String video12;
    @Column
    private String video13;
    @Column
    private String video14;
    @Column
    private String video15;
    @Column
    private String video16;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Video(User user) {
        this.video1 = "0";
        this.video2 = "0";
        this.video3 = "0";
        this.video4 = "0";
        this.video5 = "0";
        this.video6 = "0";
        this.video7 = "0";
        this.video8 = "0";
        this.video9 = "0";
        this.video10 = "0";
        this.video11 = "0";
        this.video12 = "0";
        this.video13 = "0";
        this.video14 = "0";
        this.video15 = "0";
        this.video16 = "0";
        this.user = user;
    }

    public void setVideo1(String video1) {
        this.video1 = video1;
    }

    public void setVideo2(String video2) {
        this.video2 = video2;
    }

    public void setVideo3(String video3) {
        this.video3 = video3;
    }

    public void setVideo4(String video4) {
        this.video4 = video4;
    }

    public void setVideo5(String video5) {
        this.video5 = video5;
    }

    public void setVideo6(String video6) {
        this.video6 = video6;
    }

    public void setVideo7(String video7) {
        this.video7 = video7;
    }

    public void setVideo8(String video8) {
        this.video8 = video8;
    }

    public void setVideo9(String video9) {
        this.video9 = video9;
    }

    public void setVideo10(String video10) {
        this.video10 = video10;
    }

    public void setVideo11(String video11) {
        this.video11 = video11;
    }

    public void setVideo12(String video12) {
        this.video12 = video12;
    }

    public void setVideo13(String video13) {
        this.video13 = video13;
    }

    public void setVideo14(String video14) {
        this.video14 = video14;
    }

    public void setVideo15(String video15) {
        this.video15 = video15;
    }

    public void setVideo16(String video16) {
        this.video16 = video16;
    }
}
