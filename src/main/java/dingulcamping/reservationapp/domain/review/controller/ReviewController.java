package dingulcamping.reservationapp.domain.review.controller;

import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.review.dto.ReviewCreateDto;
import dingulcamping.reservationapp.domain.review.service.ReviewService;
import dingulcamping.reservationapp.global.security.AuthUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthUtils authUtils;

    @PostMapping("/create")
    public ResponseEntity<String> createReview(@Valid ReviewCreateDto reviewCreateDto){
        Member member = authUtils.getMember();
        reviewService.createReview(reviewCreateDto, member);
        return ResponseEntity.ok("리뷰작성이 완료되었습니다.");
    }

}
