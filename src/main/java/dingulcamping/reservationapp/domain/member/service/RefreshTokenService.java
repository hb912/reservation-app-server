package dingulcamping.reservationapp.domain.member.service;

import dingulcamping.reservationapp.domain.member.entity.RefreshToken;
import dingulcamping.reservationapp.domain.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public Long getMemberId(String refreshToken){
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findById(refreshToken);
        if(findRefreshToken.isPresent()){
            return findRefreshToken.get().getMemberId();
        }
        return null;
    }
}
