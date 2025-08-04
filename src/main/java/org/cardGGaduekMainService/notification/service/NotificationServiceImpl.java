//package org.cardGGaduekMainService.notification.service;
//
//import org.cardGGaduekMainService.notification.mapper.NotificationMapper;
//import org.cardGGaduekMainService.notification.domain.NotificationVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class NotificationServiceImpl implements NotificationService {
//
//    @Autowired
//    private NotificationMapper notificationMapper;
//
//    @Override
//    public List<NotificationVO> getUserNotification(Long memberId) {
//        return notificationMapper.getNotificationByMemberId(memberId);
//    }
//}

package org.cardGGaduekMainService.notification.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.notification.domain.NotificationVO;
import org.cardGGaduekMainService.notification.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationVO> getUserNotification(Long memberId) {
        List<NotificationVO> notifications = notificationMapper.getNotificationByMemberId(memberId);

        if (notifications == null || notifications.isEmpty()) {
            throw new CustomException(ErrorCode.NOTIFICATION_NOT_FOUND);
        }

        return notifications;
    }
}
