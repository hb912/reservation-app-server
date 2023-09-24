package dingulcamping.reservationapp.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookListReq {

    private Boolean request;
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
