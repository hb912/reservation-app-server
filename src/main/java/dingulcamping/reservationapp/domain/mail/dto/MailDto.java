package dingulcamping.reservationapp.domain.mail.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDto {

    private String to;
    private String subject;
    private String context;
}
