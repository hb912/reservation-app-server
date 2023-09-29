package dingulcamping.reservationapp.domain.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SimpleMemberInfo {
    private Long _id;
    private String name;
}
