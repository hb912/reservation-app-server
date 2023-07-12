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

    public ResetPwKey findRedisKey(String redisKey){
        Optional<ResetPwKey> findByRedisKey = resetPwKeyRepository.findById(redisKey);
        if(findByRedisKey.isEmpty()){
            throw new RedisKeyExpiredException("유효기간이 지난 링크입니다.");
        }
        return findByRedisKey.get();
    }

    @Transactional
    public void deleteKey(String redisKey) {
        resetPwKeyRepository.deleteById(redisKey);
    }
}
