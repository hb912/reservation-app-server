package dingulcamping.reservationapp.domain.booking.controller;

import dingulcamping.reservationapp.domain.booking.dto.*;
import dingulcamping.reservationapp.domain.booking.entity.BookingStatus;
import dingulcamping.reservationapp.domain.room.dto.SimpleRoomDto;
import dingulcamping.reservationapp.domain.booking.service.BookingService;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.global.security.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static dingulcamping.reservationapp.domain.booking.entity.BookingStatus.BOOKING_CANCEL;
import static dingulcamping.reservationapp.domain.booking.entity.BookingStatus.CANCEL_REQ;

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
        return new ResponseEntity<>("생성 완료", HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<PageBookingInfoDto> findBookingById(Pageable pageable, HttpServletRequest request){
        Long memberId = authUtils.getMemberId();
        PageBookingInfoDto result = bookingService.getByUserId(memberId, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byDates")
    public ResponseEntity<List<Long>> findDisableRoomsByDate(@ModelAttribute SearchRoomByDateDto
                                                                              searchRoomByDateDto){
        List<Long> result=bookingService.getRoomsByDate(searchRoomByDateDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byRoom")
    public ResponseEntity<List<Date>> findDatesByRoom(@RequestParam("roomID") Long roomID){
        List<Date> result = bookingService.getDatesByRoom(roomID);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmBooking(@ModelAttribute SearchByDateDto searchByDateDto){
        bookingService.confirmBooking(searchByDateDto);
        return ResponseEntity.ok("확인 완료");
    }

    @PatchMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestBody Long bookingID){
        bookingService.changeStatus(bookingID, CANCEL_REQ);
        return ResponseEntity.ok("취소 신청 완료");
    }

    @PatchMapping("/review")
    public ResponseEntity<String> addReview(@RequestBody BookingIdDto bookingID){
        return ResponseEntity.ok("리뷰 작성 완료");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteBooking(@RequestBody Long bookingId){
        bookingService.changeStatus(bookingId, BOOKING_CANCEL);
        return ResponseEntity.ok("예약 취소 완료");
    }
}
