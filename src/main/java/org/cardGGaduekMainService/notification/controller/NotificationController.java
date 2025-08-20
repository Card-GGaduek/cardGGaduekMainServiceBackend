package org.cardGGaduekMainService.notification.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.notification.domain.NotificationVO;
import org.cardGGaduekMainService.notification.service.NotificationService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "알림 관리")
@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 기존 엔드포인트 유지 (하위 호환성)
//    @ApiOperation(value = "알림 조회", notes = "혜택 추천 알림 API")
//    @ApiResponses(
//            @ApiResponse(code = 200, message = "알림 조회 성공", response = NotificationVO.class, responseContainer = "List")
//    )
//    @GetMapping("/{memberId}")
//    public ResponseEntity<CustomApiResponse<List<NotificationVO>>> getNotification(@PathVariable Long memberId) {
//        List<NotificationVO> notifications = notificationService.getUserNotification(memberId);
//        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.NOTIFICATION_FETCH_SUCCESS, notifications));
//    }

    // 새로운 엔드포인트 추가 (로그인 사용자용)
    @ApiOperation(value = "알림 조회", notes = "혜택 추천 알림 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "알림 조회 성공", response = NotificationVO.class, responseContainer = "List")
    )
    @GetMapping("/my")
    public ResponseEntity<CustomApiResponse<List<NotificationVO>>> getMyNotifications(
            @AuthenticationPrincipal LoginMember loginMember) {

        Long memberId = loginMember.getId();
        List<NotificationVO> notifications = notificationService.getUserNotification(memberId);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.NOTIFICATION_FETCH_SUCCESS, notifications));
    }
}