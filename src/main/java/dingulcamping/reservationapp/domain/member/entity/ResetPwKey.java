package dingulcamping.reservationapp.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value="reset_pw_key",timeToLive=48*60*1000l)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResetPwKey {

    @Id
    private String redisKey;
    private Long memberId;

    public ResetPwKey(String redisKey, Long memberId) {
        this.redisKey = redisKey;
        this.memberId = memberId;
    }

}