package dingulcamping.reservationapp.domain.booking.entity;

public enum BookingStatus {
    BOOKING_REQ("예약 요청"), BOOKING_CONFIRM("예약 확정"), BOOKING_EXP("예약 만료"), BOOKING_CANCEL("예약 취소");

    final private String status;

    BookingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
