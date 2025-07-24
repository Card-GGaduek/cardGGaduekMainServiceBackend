//package org.cardGGaduekMainService.notification.controller;
//
//import org.cardGGaduekMainService.notification.domain.NotificationVO;
//import org.cardGGaduekMainService.notification.service.NotificationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/notification")
//public class NotificationController {
//
//    @Autowired
//    private NotificationService notificationService;
//
//    @GetMapping("/{memberId}")
//    public List<NotificationVO> getNotification(@PathVariable Long memberId) {
//        return notificationService.getUserNotification(memberId);
//    }
//}

package org.cardGGaduekMainService.notification.controller;

import org.cardGGaduekMainService.notification.domain.NotificationVO;
import org.cardGGaduekMainService.notification.service.NotificationService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<List<NotificationVO>>> getNotification(@PathVariable Long memberId) {
        List<NotificationVO> notifications = notificationService.getUserNotification(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.NOTIFICATION_FETCH_SUCCESS, notifications));
    }
}
