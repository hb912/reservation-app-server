package dingulcamping.reservationapp.domain.member.repository;

import dingulcamping.reservationapp.domain.member.dto.MemberInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    Page<MemberInfoDto> findAllByName(String name, Pageable pageable);
}
