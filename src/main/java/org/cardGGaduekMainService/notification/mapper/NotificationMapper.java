package org.cardGGaduekMainService.notification.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cardGGaduekMainService.notification.domain.NotificationVO;
import java.util.List;

@Mapper
public interface NotificationMapper {
    List<NotificationVO> getNotificationByMemberId(Long memberId);
}
