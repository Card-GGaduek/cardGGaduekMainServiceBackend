package org.cardGGaduekMainService.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationVO {
    private Long id;
    private Long memberId;
    private String title;
    private String message;
    private String imageUrl;
    private String linkUrl;
    private Long typeCodeId;
    private Long statusCodeId;
    private Date createdAt;
}
