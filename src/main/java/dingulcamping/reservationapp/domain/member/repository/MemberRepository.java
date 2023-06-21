package dingulcamping.reservationapp.domain.member.repository;

import dingulcamping.reservationapp.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByName(String name);
    Optional<Member> findOneByNameAndEmail(String name, String email);
    Optional<Member> findOneByEmail(String email);
    Optional<Member> findOneByRefreshToken(String refreshToken);
    Optional<Member> findOneByProviderAndEmail(String provider, String email);
    @Query("select m from Member m where m.provider='kakao' and m.email=:email")
    Optional<Member> findKakaoUserByEmail(@Param("email") String email);
    void deleteByNameAndEmail(String name, String email);
}
