package dingulcamping.reservationapp.domain.booking.repository;

import dingulcamping.reservationapp.domain.booking.dto.BookingInfoDto;
import dingulcamping.reservationapp.domain.room.dto.SimpleRoomDto;
import dingulcamping.reservationapp.domain.booking.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface BookingRepositoryCustom {
    Page<BookingInfoDto> findAllByMemberId(Long memberId, Pageable pageable);
    List<Date> findDisableDatesByRoomId(Long roomId);
    List<SimpleRoomDto> findDisableRoomsByDate(List<Date> dates);
    Page<BookingInfoDto> findRequests(String name, Pageable pageable);
    Page<BookingInfoDto> findConfirms(String name, Date date, Pageable pageable);
    List<Booking> findExistBooking(List<Date> processDates, Long roomId);
}
