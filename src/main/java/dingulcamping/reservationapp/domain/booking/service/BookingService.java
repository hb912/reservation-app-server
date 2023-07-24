package dingulcamping.reservationapp.domain.booking.service;

import dingulcamping.reservationapp.domain.booking.dto.BookingCreateDto;
import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.booking.exception.DisableBookingDateException;
import dingulcamping.reservationapp.domain.booking.exception.InvalidStartDate;
import dingulcamping.reservationapp.domain.booking.repository.BookingRepository;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.room.entity.Room;
import dingulcamping.reservationapp.domain.room.exception.NotExistRoomException;
import dingulcamping.reservationapp.domain.room.repository.RoomRepository;
import dingulcamping.reservationapp.global.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final AuthUtils authUtils;


    private void isBookingExist(List<Date> processDate, Long roomId) {
        List<Booking> existBooking = bookingRepository.findExistBooking(processDate, roomId);
        if(!existBooking.isEmpty()){
            throw new DisableBookingDateException("해당 기간에 이미 예약이 존재합니다.");
        }
    }
}
