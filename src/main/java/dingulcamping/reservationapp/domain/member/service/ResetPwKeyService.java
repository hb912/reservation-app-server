package dingulcamping.reservationapp.domain.member.service;

import dingulcamping.reservationapp.domain.member.entity.ResetPwKey;
import dingulcamping.reservationapp.domain.member.exception.RedisKeyExpiredException;
import dingulcamping.reservationapp.domain.member.exception.RedisPwKeySaveException;
import dingulcamping.reservationapp.domain.member.repository.ResetPwKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResetPwKeyService {

    private final ResetPwKeyRepository resetPwKeyRepository;

    @Transactional
    public void saveRedisKey(ResetPwKey resetPwKey){
        try {
            resetPwKeyRepository.save(resetPwKey);
        }catch(Exception e){
            throw new RedisPwKeySaveException("redis 저장에 실패했습니다.");
        }
    }
}
