package com.flexone.catchwiseserver.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class NotificationMsgDTO {
    private String subject;
    private String message;
    private String time;
}
