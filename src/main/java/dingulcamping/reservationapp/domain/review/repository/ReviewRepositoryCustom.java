package dingulcamping.reservationapp.domain.review.repository;

import dingulcamping.reservationapp.domain.review.entity.Review;
import dingulcamping.reservationapp.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryCustom {
    Page<Review> findByRoomId(Long roomId, Pageable pageable);
    List<Review> findByMemberId(Long memberId);
}
