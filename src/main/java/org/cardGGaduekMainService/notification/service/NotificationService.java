package org.cardGGaduekMainService.notification.service;

import org.cardGGaduekMainService.notification.domain.NotificationVO;
import java.util.List;

public interface NotificationService {
    List<NotificationVO> getUserNotification(Long memberId);
}