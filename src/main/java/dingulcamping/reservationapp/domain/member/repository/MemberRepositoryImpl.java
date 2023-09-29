package dingulcamping.reservationapp.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dingulcamping.reservationapp.domain.member.dto.MemberInfoDto;
import dingulcamping.reservationapp.domain.member.dto.QMemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static dingulcamping.reservationapp.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MemberInfoDto> findAllByName(String name, Pageable pageable) {
        List<MemberInfoDto> content = queryFactory.select(
                        new QMemberInfoDto(
                                member.id.as("_id"),
                                member.email,
                                member.name,
                                member.phoneNumber
                        )).from(member)
                .orderBy(member.name.desc())
                .where(nameEq(name))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(member.count())
                .from(member)
                .where(nameEq(name));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());
    }

    @Override
    public Optional<MemberInfoDto> findMemberById(Long id) {
        return Optional.ofNullable(
                queryFactory.select(new QMemberInfoDto(
                                member.id.as("_id"),
                                member.email,
                                member.name,
                                member.phoneNumber
                        )).from(member)
                        .where(member.id.eq(id))
                        .fetchOne());
    }

    private BooleanExpression nameEq(String name) {
        if (name.isBlank()) {
            return null;
        }
        return member.name.eq(name);
    }
}
