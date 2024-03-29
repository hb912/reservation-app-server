package dingulcamping.reservationapp.domain.booking.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dingulcamping.reservationapp.domain.booking.dto.BookingInfoDto;
import dingulcamping.reservationapp.domain.booking.dto.QBookingInfoDto;
import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.room.dto.QSimpleRoomDto;
import dingulcamping.reservationapp.domain.room.dto.SimpleRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static dingulcamping.reservationapp.domain.booking.entity.BookingStatus.*;
import static dingulcamping.reservationapp.domain.booking.entity.QBooking.booking;
import static dingulcamping.reservationapp.domain.member.entity.QMember.member;
import static dingulcamping.reservationapp.domain.review.entity.QReview.review;
import static dingulcamping.reservationapp.domain.room.entity.QRoom.room;

@Slf4j
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BookingInfoDto> findAllByMemberId(Long memberId, Pageable pageable) {
        List<BookingInfoDto> content = queryFactory
                .select(new QBookingInfoDto(
                        booking.id.as("_id"),
                        booking.price,
                        booking.startDate,
                        booking.endDate,
                        booking.peopleNumber,
                        booking.requirements,
                        booking.status,
                        room.id.as("roomId"),
                        room.name.as("roomName"),
                        member.name.as("memberName"),
                        review
                ))
                .from(booking)
                .leftJoin(booking.member, member)
                .leftJoin(booking.room, room)
                .leftJoin(booking.review, review)
                .where(member.id.eq(memberId))
                .orderBy(booking.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(booking.count())
                .from(booking)
                .leftJoin(booking.member, member)
                .where(member.id.eq(memberId));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());
    }

    @Override
    public List<Date> findDisableDatesByRoomId(Long roomId) {
        Date currentDate = new Date(System.currentTimeMillis());
        List<Date> disableDates = queryFactory
                .select(booking)
                .from(booking)
                .leftJoin(booking.room, room)
                .where(room.id.eq(roomId).and(booking.status.in(BOOKING_CONFIRM,BOOKING_REQ)).and(getAfter(currentDate)))
                .fetch()
                .stream()
                .map(booking -> booking.getProcessDate())
                .flatMap(List::stream)
                .filter(date -> date.after(currentDate))
                .collect(Collectors.toList());
        return disableDates;
    }

    @Override
    public List<SimpleRoomDto> findDisableRoomsByDate(List<Date> dates) {
        return queryFactory
                .select(new QSimpleRoomDto(
                        room.id.as("_id"),
                        room.name
                ))
                .distinct()
                .from(booking)
                .leftJoin(booking.room, room)
                .where(statusBooking(), getDates(dates))
                .fetch();
    }

    @Override
    public List<Booking> findExistBooking(List<Date> dates, Long roomId) {
        return queryFactory
                .selectFrom(booking)
                .leftJoin(booking.room, room)
                .where(room.id.eq(roomId).and(getDates(dates)))
                .fetch();
    }

    @Override
    public Page<BookingInfoDto> findConfirms(String name, Date date, Pageable pageable) {
        List<Date> dates= new ArrayList<>();
        dates.add(date);
        List<BookingInfoDto> content = queryFactory
                .select(new QBookingInfoDto(
                        booking.id.as("_id"),
                        booking.price,
                        booking.startDate,
                        booking.endDate,
                        booking.peopleNumber,
                        booking.requirements,
                        booking.status,
                        room.id.as("roomId"),
                        room.name.as("roomName"),
                        member.name.as("memberName"),
                        review))
                .from(booking)
                .leftJoin(booking.member, member)
                .leftJoin(booking.room, room)
                .leftJoin(booking.review, review)
                .where(statusConfirm(),getDates(dates),nameEq(name))
                .orderBy(booking.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(booking.count())
                .from(booking)
                .where(statusConfirm(),getDates(dates),nameEq(name));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());
    }

    @Override
    public Page<BookingInfoDto> findRequests(String name, Pageable pageable) {
        Date currentDate = new Date(System.currentTimeMillis());
        List<BookingInfoDto> content = queryFactory
                .select(new QBookingInfoDto(
                        booking.id.as("_id"),
                        booking.price,
                        booking.startDate,
                        booking.endDate,
                        booking.peopleNumber,
                        booking.requirements,
                        booking.status,
                        room.id.as("roomId"),
                        room.name.as("roomName"),
                        member.name.as("memberName"),
                        review))
                .from(booking)
                .from(booking)
                .leftJoin(booking.member, member)
                .leftJoin(booking.room, room)
                .leftJoin(booking.review, review)
                .where(statusReq(),getAfter(currentDate),nameEq(name))
                .orderBy(booking.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(booking.count())
                .from(booking)
                .where(statusReq(),getAfter(currentDate));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());
    }

    private List<Date> getProcessDate(Date startDate, Date endDate) {
        Date curDate = startDate;
        List<Date> processDate = new ArrayList<>();
        while (curDate.before(endDate)) {
            processDate.add(curDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(curDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            curDate = new Date(calendar.getTimeInMillis());
        }
        return processDate;
    }

    private static BooleanExpression getDates(List<Date> dates) {
        return booking.processDate.any().in(dates);
    }

    private BooleanExpression statusConfirm(){
        return booking.status.in(BOOKING_CONFIRM, BOOKING_EXP);
    }

    private BooleanExpression statusBooking(){
        return booking.status.in(BOOKING_CONFIRM, BOOKING_REQ);
    }

    private BooleanExpression statusReq(){
        return booking.status.in(BOOKING_REQ, CANCEL_REQ);
    }

    private BooleanExpression nameEq(String name) {
        if(name.isBlank()){
            return null;
        }
        return member.name.eq(name);
    }

    private BooleanExpression getAfter(Date currentDate) {
        return booking.endDate.after(currentDate);
    }

}
