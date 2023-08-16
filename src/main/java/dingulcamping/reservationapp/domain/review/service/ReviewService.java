package dingulcamping.reservationapp.domain.review.service;

import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.booking.exception.NotExistBookingException;
import dingulcamping.reservationapp.domain.booking.repository.BookingRepository;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.review.dto.ReviewCreateDto;
import dingulcamping.reservationapp.domain.review.dto.ReviewInfoDto;
import dingulcamping.reservationapp.domain.review.entity.Review;
import dingulcamping.reservationapp.domain.review.exception.InvalidMemberException;
import dingulcamping.reservationapp.domain.review.exception.ReviewIsExistException;
import dingulcamping.reservationapp.domain.review.repository.ReviewRepository;
import dingulcamping.reservationapp.domain.room.entity.Room;
import dingulcamping.reservationapp.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReviewService {

    private final RoomRepository roomRepository;
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

    public Page<ReviewInfoDto> getRoomReviews(Long roomID, Pageable pageable) {
        Room room = roomRepository.findById(roomID).orElseThrow(NotExistBookingException::new);
        return reviewRepository.findByRoom(room,pageable);
    }

    public ReviewInfoDto getBookingReview(Long bookingID) {
        Booking booking = bookingRepository.findById(bookingID).orElseThrow(NotExistBookingException::new);
        return reviewRepository.findByBooking(booking).orElseThrow(NotExistReviewException::new);
    }

    @Transactional
    public void updateReview(Long reviewID, ReviewUpdateDto reviewUpdateDto) {
        Review review = reviewRepository.findById(reviewID).orElseThrow(NotExistReviewException::new);
        review.updateReview(reviewUpdateDto);
    }

}
