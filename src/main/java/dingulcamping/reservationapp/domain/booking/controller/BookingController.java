package dingulcamping.reservationapp.domain.booking.controller;

import dingulcamping.reservationapp.domain.booking.dto.BookingCreateDto;
import dingulcamping.reservationapp.domain.booking.exception.InvalidPeopleNumberException;
import dingulcamping.reservationapp.domain.booking.exception.InvalidStartDate;
import dingulcamping.reservationapp.domain.room.dto.SimpleRoomDto;
import dingulcamping.reservationapp.domain.booking.dto.PageBookingInfoDto;
import dingulcamping.reservationapp.domain.booking.dto.SearchByDateDto;
import dingulcamping.reservationapp.domain.booking.service.BookingService;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.global.security.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/booking")
@Slf4j
public class BookingController {

    private final BookingService bookingService;
    private final AuthUtils authUtils;

    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@Valid @RequestBody BookingCreateDto bookingCreateDto,
                                                HttpServletRequest request){
        Member member = authUtils.getMember();
        bookingService.createBooking(bookingCreateDto,member);
        return ResponseEntity.ok("생성완료");
    }

    @GetMapping("/user")
    public ResponseEntity<PageBookingInfoDto> findBookingById(Pageable pageable, HttpServletRequest request){
        Long memberId = authUtils.getMemberId();
        PageBookingInfoDto result = bookingService.getByUserId(memberId, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byDates")
    public ResponseEntity<List<SimpleRoomDto>> findDisableRoomsByDate(@ModelAttribute SearchByDateDto
    searchByDateDto){
        List<SimpleRoomDto> result=bookingService.getRoomsByDate(searchByDateDto);
        return ResponseEntity.ok(result);
    }

}
