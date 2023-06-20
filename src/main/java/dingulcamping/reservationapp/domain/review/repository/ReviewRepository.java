package dingulcamping.reservationapp.domain.review.repository;

import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.review.entity.Review;
import dingulcamping.reservationapp.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long>, ReviewRepositoryCustom {
    Optional<Review> findByBooking(Booking booking);
}
