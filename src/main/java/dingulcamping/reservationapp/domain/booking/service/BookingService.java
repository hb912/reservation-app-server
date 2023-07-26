package dingulcamping.reservationapp.domain.booking.service;

import dingulcamping.reservationapp.domain.booking.dto.BookingCreateDto;
import dingulcamping.reservationapp.domain.booking.dto.BookingInfoDto;
import dingulcamping.reservationapp.domain.booking.dto.PageBookingInfoDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public void createBooking(BookingCreateDto bookingCreateDto, Member member){
        Date today = new Date(System.currentTimeMillis());
        if(bookingCreateDto.getEndDate().compareTo(bookingCreateDto.getStartDate())<=0){
            throw new InvalidStartDate("종료날짜가 시작날짜보다 작거나 같습니다.");
        }
        if(bookingCreateDto.getStartDate().before(today)){
            throw new InvalidStartDate("과거의 예약은 진행할 수 없습니다");
        }

        int peopleNumber = bookingCreateDto.getPeopleNumber();
        Room room = roomRepository.findById(bookingCreateDto.getRoomId()).orElseThrow(NotExistRoomException::new);
        if(peopleNumber>room.getMaxPeople()||peopleNumber<room.getMinPeople()){
            throw new InvalidPeopleNumberException("정원이 맞지 않습니다.");
        }

        Booking booking = new Booking(bookingCreateDto,member,room);
        isBookingExist(booking.getProcessDate(), bookingCreateDto.getRoomId());
        bookingRepository.save(booking);
    }

    public PageBookingInfoDto getByUserId(Long memberId, Pageable pageable){

        Page<BookingInfoDto> bookings = bookingRepository.findAllByMemberId(memberId, pageable);
        return new PageBookingInfoDto(bookings.getContent(), bookings.getTotalPages());
    }

    private void isBookingExist(List<Date> processDate, Long roomId) {
        List<Booking> existBooking = bookingRepository.findExistBooking(processDate, roomId);
        if(!existBooking.isEmpty()){
            throw new DisableBookingDateException("해당 기간에 이미 예약이 존재합니다.");
        }
    }

    public List<SimpleRoomDto> getRoomsByDate(SearchByDateDto searchByDateDto) {
        Date startDate = new Date(searchByDateDto.getStartDate().getTime());
        Date endDate = new Date(searchByDateDto.getEndDate().getTime());
        List<Date> dates = getProcessDate(startDate, endDate);
        List<SimpleRoomDto> disableRoomsByDate = bookingRepository.findDisableRoomsByDate(dates);
        List<SimpleRoomDto> disableRoomByPeopleNumber =
                roomRepository.findDisableRoomByPeopleNumber(searchByDateDto.getPeopleNumber());
        return Stream.concat(disableRoomsByDate.stream(), disableRoomByPeopleNumber.stream()).distinct()
                .collect(Collectors.toList());
    }
}
