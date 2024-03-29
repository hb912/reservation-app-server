package dingulcamping.reservationapp.domain.review.controller;

import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.review.dto.ReviewCreateDto;
import dingulcamping.reservationapp.domain.review.dto.ReviewInfoDto;
import dingulcamping.reservationapp.domain.review.dto.ReviewUpdateDto;
import dingulcamping.reservationapp.domain.review.service.ReviewService;
import dingulcamping.reservationapp.global.security.AuthUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthUtils authUtils;

    @PostMapping("/create")
    public ResponseEntity<String> createReview(@Valid @RequestBody ReviewCreateDto reviewCreateDto){
        Member member = authUtils.getMember();
        reviewService.createReview(reviewCreateDto, member);
        return ResponseEntity.ok("리뷰작성이 완료되었습니다.");
    }

    @GetMapping("")
    public ResponseEntity<Page<ReviewInfoDto>> findByRoomId(Long roomID, Pageable pageable){
        Page<ReviewInfoDto> results = reviewService.getRoomReviews(roomID, pageable);
        log.info("result={}",results);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/booking")
    public ResponseEntity<ReviewInfoDto> findByBookingId(Long bookingID){
        log.info("bookingid={}",bookingID);
        ReviewInfoDto result=reviewService.getBookingReview(bookingID);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{reviewID}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewID,
                                               @RequestBody ReviewUpdateDto reviewUpdateDto){
        reviewService.updateReview(reviewID,reviewUpdateDto);
        log.info("content={}",reviewUpdateDto.getContent());
        return ResponseEntity.ok("수정 완료");
    }

    @DeleteMapping("/{reviewID}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewID){
        reviewService.deleteReview(reviewID);
        return ResponseEntity.ok("삭제 완료");
    }
}
