package dingulcamping.reservationapp.domain.review.repository;

import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.review.dto.ReviewInfoDto;
import dingulcamping.reservationapp.domain.review.entity.Review;
import dingulcamping.reservationapp.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {
    Page<ReviewInfoDto> findByRoom(Room room, Pageable pageable);
    List<Review> findByMemberId(Long memberId);
    Optional<ReviewInfoDto> findByBooking(Booking booking);
}
