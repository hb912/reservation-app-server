package dingulcamping.reservationapp.domain.booking.repository;

import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface BookingRepositoryCustom {
    Page<Booking> findAllByMemberId(Long memberId, Pageable pageable);
    List<Date> findDisableDatesByRoomId(Long roomId);
    List<Room> findDisableRoomsByDate(List<Date> dates);
    List<Booking> findRequests();
    List<Booking> findConfirms();
}
