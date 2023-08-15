package dingulcamping.reservationapp.domain.review.entity;

import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.review.dto.ReviewCreateDto;
import dingulcamping.reservationapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Double grade;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="booking_id")
    private Booking booking;

    public Review(String title, String content, Double grade, Booking booking) {
        this.title = title;
        this.content = content;
        this.grade = grade;
        this.booking = booking;
        booking.createReview(this);
    }

    public Review(ReviewCreateDto reviewCreateDto, Booking booking, Member member) {
        this(reviewCreateDto.getTitle(), reviewCreateDto.getContent(), reviewCreateDto.getGrade(), booking);
    }
}
