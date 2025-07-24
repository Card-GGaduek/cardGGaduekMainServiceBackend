package org.cardGGaduekMainService.notification.service;

import org.cardGGaduekMainService.notification.mapper.NotificationMapper;
import org.cardGGaduekMainService.notification.domain.NotificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public List<NotificationVO> getUserNotifications(Long memberId) {
        return notificationMapper.getNotificationsByMemberId(memberId);
    }
}
