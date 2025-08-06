package org.cardGGaduekMainService.notification.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.notification.domain.NotificationVO;
import org.cardGGaduekMainService.notification.service.NotificationService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 기존 엔드포인트 유지 (하위 호환성)
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<List<NotificationVO>>> getNotification(@PathVariable Long memberId) {
        List<NotificationVO> notifications = notificationService.getUserNotification(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.NOTIFICATION_FETCH_SUCCESS, notifications));
    }

    // 새로운 엔드포인트 추가 (로그인 사용자용)
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<NotificationVO>>> getMyNotifications(
            @AuthenticationPrincipal LoginMember loginMember) {

        Long memberId = loginMember.getId();
        List<NotificationVO> notifications = notificationService.getUserNotification(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.NOTIFICATION_FETCH_SUCCESS, notifications));
    }
}