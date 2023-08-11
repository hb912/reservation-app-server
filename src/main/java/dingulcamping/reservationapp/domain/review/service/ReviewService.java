package dingulcamping.reservationapp.domain.review.service;

import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.booking.exception.NotExistBookingException;
import dingulcamping.reservationapp.domain.booking.repository.BookingRepository;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.review.dto.ReviewCreateDto;
import dingulcamping.reservationapp.domain.review.entity.Review;
import dingulcamping.reservationapp.domain.review.exception.InvalidMemberException;
import dingulcamping.reservationapp.domain.review.exception.ReviewIsExistException;
import dingulcamping.reservationapp.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public void createReview(ReviewCreateDto reviewCreateDto, Member member) {
        Booking booking =
                bookingRepository.findById(reviewCreateDto.getBookingID()).orElseThrow(NotExistBookingException::new);
        reviewRepository.findByBooking(booking).ifPresent(r -> {
            throw new ReviewIsExistException();
        });
        if(member.getId()!=booking.getMember().getId()){
            throw new InvalidMemberException();
        }

        Review review = new Review(reviewCreateDto, booking, member);
        reviewRepository.save(review);
    }
}
