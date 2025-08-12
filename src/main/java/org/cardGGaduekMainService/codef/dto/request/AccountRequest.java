package org.cardGGaduekMainService.codef.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    @JsonProperty("countryCode")
    private String countryCode = "KR";

    @JsonProperty("businessType")
    private String businessType;

    @JsonProperty("organization")
    private String organization;

    @JsonProperty("clientType")
    private String clientType;

    @JsonProperty("loginType")
    private String loginType;

    @JsonProperty("id")
    private String id;

    @JsonProperty("password")
    private String password;

}
