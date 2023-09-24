package dingulcamping.reservationapp.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import dingulcamping.reservationapp.domain.member.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoDto {
    private Long _id;
    private String email;
    private String name;
    private String phoneNumber;

    @QueryProjection
    public MemberInfoDto(Long _id, String email, String name, String phoneNumber) {
        this._id = _id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
