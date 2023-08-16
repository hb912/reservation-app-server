package dingulcamping.reservationapp.domain.member.entity;

import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.member.dto.RegisterReqDto;
import dingulcamping.reservationapp.domain.member.dto.MemberUpdateDto;
import dingulcamping.reservationapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static dingulcamping.reservationapp.domain.member.entity.Role.USER;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable = false)
    private String email;
    private String name;
    private String password;
    private String phoneNumber;

    @Enumerated(value=EnumType.STRING)
    private Role role;
    private String provider;

    @OneToMany(mappedBy="member", fetch= FetchType.LAZY)
    private List<Booking> bookings=new ArrayList<>();

    public Member(String email,String name, String password,  String phoneNumber) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role= USER;
    }

    public Member(String email, String name, String password, String phoneNumber, Role role,
                  String provider) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.provider = provider;
    }

    public Member(RegisterReqDto registerReq){
        this.email=registerReq.getEmail();
        this.name=registerReq.getName();
        this.phoneNumber=registerReq.getPhoneNumber();
        this.role= USER;
        this.provider=null;
    }

    public void setProvider(String provider){
        this.provider=provider;
    }

    public void setEncryptedPassword(String encryptedPassword){
        this.password=encryptedPassword;
    }

    public void updateMember(MemberUpdateDto memberUpdateDto){
        if(memberUpdateDto.getName()!=null){
            this.name= memberUpdateDto.getName();
        }
        if(memberUpdateDto.getPhoneNumber()!=null){
            this.phoneNumber= memberUpdateDto.getPhoneNumber();
        }
    }

    public void addBooking(Booking booking){
        this.bookings.add(booking);
    }
}
