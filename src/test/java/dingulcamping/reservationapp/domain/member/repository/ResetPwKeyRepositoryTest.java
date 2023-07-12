package dingulcamping.reservationapp.domain.member.repository;

import dingulcamping.reservationapp.domain.member.entity.ResetPwKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ResetPwKeyRepositoryTest {

    @Autowired
    ResetPwKeyRepository resetPwKeyRepository;

    @Test
    public void deleteKeys(){
        //given
        Long memberId=60L;
        String randomkey1 = UUID.randomUUID().toString();
        String randomkey2 = UUID.randomUUID().toString();
        String randomkey3 = UUID.randomUUID().toString();
        resetPwKeyRepository.save(new ResetPwKey(randomkey1,memberId));
        resetPwKeyRepository.save(new ResetPwKey(randomkey2,memberId));
        resetPwKeyRepository.save(new ResetPwKey(randomkey3,memberId));

        //when
        resetPwKeyRepository.deleteById(randomkey1);

        //then
        Optional<ResetPwKey> findById1 = resetPwKeyRepository.findById(randomkey1);
        Optional<ResetPwKey> findById2 = resetPwKeyRepository.findById(randomkey2);
        Optional<ResetPwKey> findById3 = resetPwKeyRepository.findById(randomkey3);

        assertThat(findById1.isEmpty()).isTrue();
        assertThat(findById2.isEmpty()).isFalse();
        assertThat(findById3.isEmpty()).isFalse();
    }
}