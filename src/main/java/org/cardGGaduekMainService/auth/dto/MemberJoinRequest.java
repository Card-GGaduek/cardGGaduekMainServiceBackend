package org.cardGGaduekMainService.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinRequest {

    private String email;
    private String password;
    private String name;
    private String phone;

}
