package dingulcamping.reservationapp.domain.booking.controller;

import dingulcamping.reservationapp.domain.booking.dto.BookingCreateDto;
import dingulcamping.reservationapp.domain.booking.service.BookingService;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.global.security.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        Member member = authUtils.getMember(request);
        bookingService.createBooking(bookingCreateDto,member);
        return ResponseEntity.ok("생성완료");
    }
}
