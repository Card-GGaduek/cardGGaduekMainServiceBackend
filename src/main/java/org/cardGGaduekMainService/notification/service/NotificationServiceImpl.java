package org.cardGGaduekMainService.notification.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.notification.domain.NotificationVO;
import org.cardGGaduekMainService.notification.mapper.NotificationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationVO> getUserNotification(Long memberId) {
        List<NotificationVO> notifications = notificationMapper.getNotificationByMemberId(memberId);

        // 빈 리스트인 경우 예외를 던지지 않고 빈 리스트 반환
        // 프론트엔드에서 "알림이 없습니다" UI를 표시할 수 있도록 함
        if (notifications == null) {
            return Collections.emptyList();
        }

        return notifications;
    }
}