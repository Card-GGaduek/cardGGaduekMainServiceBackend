package org.cardGGaduekMainService.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TokenResponse {
    private String accessToken;
    private User user;

}
