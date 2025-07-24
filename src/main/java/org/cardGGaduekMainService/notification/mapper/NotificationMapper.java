package org.cardGGaduekMainService.notification.mapper;

import org.cardGGaduekMainService.notification.domain.NotificationVO;
import java.util.List;

public interface NotificationMapper {
    List<NotificationVO> getNotificationsByMemberId(Long memberId);
}
