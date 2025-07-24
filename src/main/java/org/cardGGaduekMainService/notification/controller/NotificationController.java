package org.cardGGaduekMainService.notification.controller;

import org.cardGGaduekMainService.notification.domain.NotificationVO;
import org.cardGGaduekMainService.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{memberId}")
    public List<NotificationVO> getNotifications(@PathVariable Long memberId) {
        return notificationService.getUserNotifications(memberId);
    }
}
