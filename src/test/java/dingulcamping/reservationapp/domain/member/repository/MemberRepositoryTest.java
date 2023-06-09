package dingulcamping.reservationapp.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.entity.QMember;
import dingulcamping.reservationapp.domain.member.entity.Role;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static dingulcamping.reservationapp.domain.member.entity.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void before(){
        Optional<Member> hb912 = memberRepository.findOneByUserId("hb912");
        Optional<Member> hb915 = memberRepository.findOneByUserId("hb915");

        if(hb912.isPresent()){
            memberRepository.deleteByUserId(hb912.get().getUserId());
            System.out.println("hb912 삭제");
        }
        if(hb915.isPresent()){
            memberRepository.deleteByUserId(hb915.get().getUserId());
            System.out.println("hb915 삭제");
        }
        em.flush();     //*주의 save의 우선순위가 더 높기 때문에 flush를 사용하지 않으면 delete가 반영되지 않는다.
    }

    /**
     * test save, findById
     */
    @Test
    @Rollback(value = false)
    public void saveAndSelectById(){
        Member memberA=new Member("hb912","memberA","12344456","ab@ab.com","010-5031-8478");
        Member memberB=new Member("hb915","memberB","123444577","ab@abb.com","010-4444-8478");

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Optional<Member> findMemberA = memberRepository.findOneByUserId(memberA.getUserId());
        Optional<Member> findMemberB = memberRepository.findOneByUserId(memberB.getUserId());

        assertThat(findMemberA.isPresent()).isTrue();
        assertThat(findMemberA.get()).isEqualTo(memberA);
        assertThat(findMemberB.isPresent()).isTrue();
        assertThat(findMemberB.get()).isEqualTo(memberB);

    }

    @Test
//    @Rollback(value = false)
    public void findKakaoMemberByEmail(){
        Member memberA=new Member("hb912","memberA","12344456","ab@ab.com","010-5031-8478", USER.toString(),"kakao");
        Member memberB=new Member("hb915","memberB","123444577","ab@abb.com","010-4444-8478", USER.toString(),null);

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Optional<Member> findMemberA = memberRepository.findKakaoUserByEmail(memberA.getEmail());
        Optional<Member> findMemberB = memberRepository.findKakaoUserByEmail(memberB.getEmail());

        assertThat(findMemberA.isPresent()).isTrue();
        if(findMemberA.isPresent()) {
            assertThat(findMemberA.get()).isEqualTo(memberA);
        }
        assertThat(findMemberB.isEmpty()).isTrue();
        if(findMemberB.isPresent()) {
            assertThat(findMemberB.get()).isEqualTo(memberB);
        }
    }

}