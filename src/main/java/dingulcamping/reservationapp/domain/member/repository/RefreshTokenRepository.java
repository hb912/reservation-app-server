package dingulcamping.reservationapp.domain.member.repository;

import dingulcamping.reservationapp.domain.member.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
