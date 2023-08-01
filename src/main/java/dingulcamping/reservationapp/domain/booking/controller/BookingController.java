package dingulcamping.reservationapp.domain.booking.controller;

import dingulcamping.reservationapp.domain.booking.dto.BookingCreateDto;
import dingulcamping.reservationapp.domain.booking.dto.SearchByDateDto;
import dingulcamping.reservationapp.domain.room.dto.SimpleRoomDto;
import dingulcamping.reservationapp.domain.booking.dto.PageBookingInfoDto;
import dingulcamping.reservationapp.domain.booking.dto.SearchRoomByDateDto;
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

import java.sql.Date;
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
    public ResponseEntity<List<SimpleRoomDto>> findDisableRoomsByDate(@ModelAttribute SearchRoomByDateDto
                                                                              searchRoomByDateDto){
        List<SimpleRoomDto> result=bookingService.getRoomsByDate(searchRoomByDateDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byRoom")
    public ResponseEntity<List<Date>> findDatesByRoom(@RequestParam("roomID") Long roomID){
        List<Date> result = bookingService.getDatesByRoom(roomID);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmBooking(@RequestBody SearchByDateDto searchByDateDto){
        bookingService.confirmBooking(searchByDateDto);
        return ResponseEntity.ok("확인 완료");
    }

    @PatchMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestBody Long bookingID){
        bookingService.changeStatus(bookingID, BookingStatus.CANCEL_REQ);
        return ResponseEntity.ok("취소 신청 완료");
    }


}
