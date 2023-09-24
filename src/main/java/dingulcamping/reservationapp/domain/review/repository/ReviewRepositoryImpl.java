package dingulcamping.reservationapp.domain.review.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.review.dto.QReviewInfoDto;
import dingulcamping.reservationapp.domain.review.dto.ReviewInfoDto;
import dingulcamping.reservationapp.domain.review.entity.Review;
import dingulcamping.reservationapp.domain.room.entity.QRoom;
import dingulcamping.reservationapp.domain.room.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static dingulcamping.reservationapp.domain.booking.entity.QBooking.booking;
import static dingulcamping.reservationapp.domain.member.entity.QMember.member;
import static dingulcamping.reservationapp.domain.review.entity.QReview.review;
import static dingulcamping.reservationapp.domain.room.entity.QRoom.*;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewInfoDto> findByRoom(Room roomDto, Pageable pageable) {
        List<ReviewInfoDto> reviews = queryFactory
                .select(new QReviewInfoDto(
                        review.id.as("_id"),
                        room.id.as("roomId"),
                        room.name.as("roomName"),
                        member.id.as("memberId"),
                        member.name.as("memberName"),
                        booking.id.as("bookingID"),
                        review.title,
                        review.content,
                        review.grade
                ))
                .from(review)
                .innerJoin(review.booking, booking)
                .innerJoin(booking.room, room)
                .innerJoin(booking.member, member)
                .where(room.id.eq(roomDto.getId()))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .innerJoin(review.booking, booking)
                .innerJoin(booking.room, room)
                .where(room.id.eq(roomDto.getId()));

        return PageableExecutionUtils.getPage(reviews, pageable, () -> countQuery.fetchOne());
    }

    @Override
    public Optional<ReviewInfoDto> findByBooking(Booking bookingDto) {
        return Optional.ofNullable(
                queryFactory
                        .select(new QReviewInfoDto(
                                review.id.as("_id"),
                                room.id.as("roomId"),
                                room.name.as("roomName"),
                                member.id.as("memberId"),
                                member.name.as("memberName"),
                                booking.id.as("bookingID"),
                                review.title,
                                review.content,
                                review.grade
                        ))
                        .from(review)
                        .innerJoin(review.booking, booking)
                        .innerJoin(booking.room, room)
                        .innerJoin(booking.member, member)
                        .where(booking.id.eq(bookingDto.getId()))
                        .fetchOne()
        );
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
