package dingulcamping.reservationapp.domain.booking.repository;

import dingulcamping.reservationapp.domain.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long>, BookingRepositoryCustom {
}
