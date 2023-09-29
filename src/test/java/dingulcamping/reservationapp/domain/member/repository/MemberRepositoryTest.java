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
        Optional<Member> memberA = memberRepository.findOneByNameAndEmail("memberA","ab@ab.com");
        Optional<Member> memberB = memberRepository.findOneByNameAndEmail("memberA","ab@abb.com");

        if(memberA.isPresent()){
            memberRepository.deleteById(memberA.get().getId());
            System.out.println("memberA 삭제");
        }
        if(memberB.isPresent()){
            memberRepository.deleteById(memberB.get().getId());
            System.out.println("memberB 삭제");
        }
        em.flush();     //*주의 save의 우선순위가 더 높기 때문에 flush를 사용하지 않으면 delete가 반영되지 않는다.
    }

    /**
     * test save, findById
     */
    @Test
    public void saveAndSelectById(){
        Member memberA=new Member("ab@ab.com","memberA","12344456","010-5031-8478");
        Member memberB=new Member("ab@abb.com","memberB","123444577","010-4444-8478");

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Optional<Member> findMemberA = memberRepository.findOneByNameAndEmail(memberA.getName(),memberA.getEmail());
        Optional<Member> findMemberB = memberRepository.findOneByNameAndEmail(memberB.getName(),memberB.getEmail());

        assertThat(findMemberA.isPresent()).isTrue();
        assertThat(findMemberA.get()).isEqualTo(memberA);
        assertThat(findMemberB.isPresent()).isTrue();
        assertThat(findMemberB.get()).isEqualTo(memberB);

    }

    @Test
//    @Rollback(value = false)
    public void findKakaoMemberByEmail(){
        Member memberA=new Member("ab@ab.com","memberA","12344456","010-5031-8478", USER,"kakao");
        Member memberB=new Member("ab@abb.com","memberB","123444577","010-4444-8478", USER,null);

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

    @Test
    public void deleteByNameAndEmail(){
        //given
        String name1 = "memberA";
        String email1 = "ab@ab.com";
        String email2 = "ab@abb.com";
        //when
        memberRepository.deleteByNameAndEmail(name1,email1);
        //then
        Optional<Member> findByEmail = memberRepository.findOneByEmail(email1);
        Optional<Member> findByEmail2 = memberRepository.findOneByEmail(email2);

        assertThat(findByEmail.isEmpty()).isTrue();
        assertThat(findByEmail2.isEmpty()).isFalse();

    }

}