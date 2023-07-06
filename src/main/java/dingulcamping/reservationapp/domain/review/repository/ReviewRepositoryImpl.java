package dingulcamping.reservationapp.domain.review.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dingulcamping.reservationapp.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static dingulcamping.reservationapp.domain.booking.entity.QBooking.booking;
import static dingulcamping.reservationapp.domain.member.entity.QMember.member;
import static dingulcamping.reservationapp.domain.review.entity.QReview.review;
import static dingulcamping.reservationapp.domain.room.entity.QRoom.room;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Review> findByRoomId(Long roomId, Pageable pageable) {
        List<Review> reviews = queryFactory
                .selectFrom(review)
                .innerJoin(review.booking, booking)
                .innerJoin(booking.room, room)
                .where(room.id.eq(roomId))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .innerJoin(review.booking, booking)
                .innerJoin(booking.room, room)
                .where(room.id.eq(roomId));

        return PageableExecutionUtils.getPage(reviews,pageable, ()->countQuery.fetchOne());
    }

    @Override
    public List<Review> findByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(review)
                .innerJoin(review.booking, booking)
                .innerJoin(booking.member, member)
                .where(member.id.eq(memberId))
                .fetch();
    }
}
