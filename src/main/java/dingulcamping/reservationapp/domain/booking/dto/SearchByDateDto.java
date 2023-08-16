package dingulcamping.reservationapp.domain.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class SearchByDateDto {

    private String startDate;
    private String endDate;
    private Long roomID;

    public Date convertStartDate(){
        String replaceDate = this.startDate.replace("\"", "");
        log.info("startDate={}",replaceDate);
        return getDate(replaceDate);
    }

    public Date convertEndDate(){
        String replaceDate = this.endDate.replace("\"", "");
        log.info("endDate={}",replaceDate);

        return getDate(replaceDate);
    }

    public Long getRoomID() {
        return roomID;
    }

    private Date getDate(String replaceDate) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = isoFormat.parse(replaceDate);
            long timeInMillis = utilDate.getTime();
            Date sqlDate = new Date(timeInMillis);

            System.out.println("ISO Date-Time String: " + replaceDate);
            System.out.println("java.sql.Date: " + sqlDate);
            return sqlDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
