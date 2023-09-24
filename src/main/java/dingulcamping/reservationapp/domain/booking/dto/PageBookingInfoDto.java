package dingulcamping.reservationapp.domain.booking.dto;

import lombok.Getter;

import java.sql.Date;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class PageBookingInfoDto {
    private List<BookingInfoDto> bookingInfos;
    private List<CheckPassed> checkPassed;
    private int totalPage;

    public PageBookingInfoDto(List<BookingInfoDto> bookingInfos, int totalPage) {
        this.bookingInfos = bookingInfos;
        Date today = new Date(System.currentTimeMillis());
        this.checkPassed =
                bookingInfos.stream().map(booking -> new CheckPassed(booking.getEndDate().before(today))).toList();
        this.totalPage = totalPage;
    }
}
