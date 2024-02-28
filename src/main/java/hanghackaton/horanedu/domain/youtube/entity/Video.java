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
    private Integer video1;
    @Column
    private Boolean zzim1;
    @Column
    private Integer video2;
    @Column
    private Boolean zzim2;
    @Column
    private Integer video3;
    @Column
    private Boolean zzim3;
    @Column
    private Integer video4;
    @Column
    private Boolean zzim4;
    @Column
    private Integer video5;
    @Column
    private Boolean zzim5;
    @Column
    private Integer video6;
    @Column
    private Boolean zzim6;
    @Column
    private Integer video7;
    @Column
    private Boolean zzim7;
    @Column
    private Integer video8;
    @Column
    private Boolean zzim8;
    @Column
    private Integer video9;
    @Column
    private Boolean zzim9;
    @Column
    private Integer video10;
    @Column
    private Boolean zzim10;
    @Column
    private Integer video11;
    @Column
    private Boolean zzim11;
    @Column
    private Integer video12;
    @Column
    private Boolean zzim12;
    @Column
    private Integer video13;
    @Column
    private Boolean zzim13;
    @Column
    private Integer video14;
    @Column
    private Boolean zzim14;
    @Column
    private Integer video15;
    @Column
    private Boolean zzim15;
    @Column
    private Integer video16;
    @Column
    private Boolean zzim16;
    @Column
    private Long lastViewed;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Video(User user) {
        this.video1 = 0;
        this.zzim1 = false;
        this.video2 = 0;
        this.zzim2 = false;
        this.video3 = 0;
        this.zzim3 = false;
        this.video4 = 0;
        this.zzim4 = false;
        this.video5 = 0;
        this.zzim5 = false;
        this.video6 = 0;
        this.zzim6 = false;
        this.video7 = 0;
        this.zzim7 = false;
        this.video8 = 0;
        this.zzim8 = false;
        this.video9 = 0;
        this.zzim9 = false;
        this.video10 = 0;
        this.zzim10 = false;
        this.video11 = 0;
        this.zzim11 = false;
        this.video12 = 0;
        this.zzim12 = false;
        this.video13 = 0;
        this.zzim13 = false;
        this.video14 = 0;
        this.zzim14 = false;
        this.video15 = 0;
        this.zzim15 = false;
        this.video16 = 0;
        this.zzim16 = false;
        this.lastViewed = 0L;
        this.user = user;
    }

    public void setVideo1(Integer video1) {
        this.video1 = video1;
    }

    public void setVideo2(Integer video2) {
        this.video2 = video2;
    }

    public void setVideo3(Integer video3) {
        this.video3 = video3;
    }

    public void setVideo4(Integer video4) {
        this.video4 = video4;
    }

    public void setVideo5(Integer video5) {
        this.video5 = video5;
    }

    public void setVideo6(Integer video6) {
        this.video6 = video6;
    }

    public void setVideo7(Integer video7) {
        this.video7 = video7;
    }

    public void setVideo8(Integer video8) {
        this.video8 = video8;
    }

    public void setVideo9(Integer video9) {
        this.video9 = video9;
    }

    public void setVideo10(Integer video10) {
        this.video10 = video10;
    }

    public void setVideo11(Integer video11) {
        this.video11 = video11;
    }

    public void setVideo12(Integer video12) {
        this.video12 = video12;
    }

    public void setVideo13(Integer video13) {
        this.video13 = video13;
    }

    public void setVideo14(Integer video14) {
        this.video14 = video14;
    }

    public void setVideo15(Integer video15) {
        this.video15 = video15;
    }

    public void setVideo16(Integer video16) {
        this.video16 = video16;
    }

    public void setLastViewed(Long videoId) {
        this.lastViewed = videoId;
    }

    public void setZzim1(Boolean zzim1) {
        this.zzim1 = zzim1;
    }

    public void setZzim2(Boolean zzim2) {
        this.zzim2 = zzim2;
    }

    public void setZzim3(Boolean zzim3) {
        this.zzim3 = zzim3;
    }

    public void setZzim4(Boolean zzim4) {
        this.zzim4 = zzim4;
    }

    public void setZzim5(Boolean zzim5) {
        this.zzim5 = zzim5;
    }

    public void setZzim6(Boolean zzim6) {
        this.zzim6 = zzim6;
    }

    public void setZzim7(Boolean zzim7) {
        this.zzim7 = zzim7;
    }

    public void setZzim8(Boolean zzim8) {
        this.zzim8 = zzim8;
    }

    public void setZzim9(Boolean zzim9) {
        this.zzim9 = zzim9;
    }

    public void setZzim10(Boolean zzim10) {
        this.zzim10 = zzim10;
    }

    public void setZzim11(Boolean zzim11) {
        this.zzim11 = zzim11;
    }

    public void setZzim12(Boolean zzim12) {
        this.zzim12 = zzim12;
    }

    public void setZzim13(Boolean zzim13) {
        this.zzim13 = zzim13;
    }

    public void setZzim14(Boolean zzim14) {
        this.zzim14 = zzim14;
    }

    public void setZzim15(Boolean zzim15) {
        this.zzim15 = zzim15;
    }

    public void setZzim16(Boolean zzim16) {
        this.zzim16 = zzim16;
    }
}
