package dingulcamping.reservationapp.domain.member.entity;

import dingulcamping.reservationapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable = false)
    private String userId;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;
    private String provider;
    private String refreshToken;

    //TODO
//    @OneToMany(mappedBy="booking", fetch= FetchType.LAZY)
//    private List<Booking> bookings=new ArrayList<>();

    public Member(String userId, String name, String password, String email, String phoneNumber) {
        this.userId=userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role=String.valueOf(Role.USER);
    }

    public Member(String userId,String name, String password, String email, String phoneNumber, String role,
                  String provider) {
        this.userId=userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.provider = provider;
    }

    public void changeRefreshToken(String refreshToken){
        this.refreshToken=refreshToken;
    }

    public void changePassword(String password){
        this.password=password;
    }

}
