package dingulcamping.reservationapp.domain.member.service;

import dingulcamping.reservationapp.domain.member.entity.ResetPwKey;
import dingulcamping.reservationapp.domain.member.exception.RedisKeyExpiredException;
import dingulcamping.reservationapp.domain.member.exception.RedisPwKeySaveException;
import dingulcamping.reservationapp.domain.member.repository.ResetPwKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ResetPwKeyService {

    private final ResetPwKeyRepository resetPwKeyRepository;

    @Transactional
    public void saveRedisKey(ResetPwKey resetPwKey){
        try {
            resetPwKeyRepository.save(resetPwKey);
        }catch(Exception e){
            log.error("redis error = {}", e.getMessage());
            throw new RedisPwKeySaveException();
        }
    }

    public ResetPwKey findRedisKey(String redisKey){
        Optional<ResetPwKey> findByRedisKey = resetPwKeyRepository.findById(redisKey);
        if(findByRedisKey.isEmpty()){
            throw new RedisKeyExpiredException();
        }
        return findByRedisKey.get();
    }

    @Transactional
    public void deleteKey(String redisKey) {
        resetPwKeyRepository.deleteById(redisKey);
    }
}
