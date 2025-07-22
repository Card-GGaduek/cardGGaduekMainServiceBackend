package org.cardGGaduekMainService.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberFindDTO {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private boolean is_active;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;
    private Long loginTypeCodeId;
    private Long socialId;
}
