package dingulcamping.reservationapp.domain.admin.controller;

import dingulcamping.reservationapp.domain.admin.dto.BookListReq;
import dingulcamping.reservationapp.domain.booking.dto.BookingInfoDto;
import dingulcamping.reservationapp.domain.booking.service.BookingService;
import dingulcamping.reservationapp.domain.member.dto.MemberIdDto;
import dingulcamping.reservationapp.domain.member.dto.MemberInfoDto;
import dingulcamping.reservationapp.domain.member.dto.UserIdDto;
import dingulcamping.reservationapp.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {

    private final MemberService memberService;
    private final BookingService bookingService;

    @GetMapping("/users")
    public ResponseEntity<Page<MemberInfoDto>> getUserByName(@RequestParam("name") String name,Pageable pageable){
        Page<MemberInfoDto> memberList = memberService.getAllByName(name,pageable);
        return ResponseEntity.ok(memberList);
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteMember(@RequestBody UserIdDto userIdDto){
        memberService.deleteMember(userIdDto.getUserId());
        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/books")
    public ResponseEntity<Page<BookingInfoDto>> getAllBooks(@ModelAttribute BookListReq bookListReq, Pageable pageable){
        if(bookListReq.getRequest()){
            Page<BookingInfoDto> bookLists = bookingService.getRequests(bookListReq.getName(),pageable);
            return ResponseEntity.ok(bookLists);
        }else{
            Page<BookingInfoDto> bookLists = bookingService.getBooks(bookListReq.getName(),
                    new Date(bookListReq.getDate().getTime()),
                    pageable);
            return ResponseEntity.ok(bookLists);
        }
    }

}
