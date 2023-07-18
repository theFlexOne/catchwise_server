package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.dto.NotificationMsgDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class NotificationMsgController {

    @MessageMapping("/notifications")
    @SendTo("/topic/notifications")
    public NotificationMsgDTO sendNotification(@Payload NotificationMsgDTO notificationMsgDTO) {
        notificationMsgDTO.setTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        return notificationMsgDTO;
    }

}