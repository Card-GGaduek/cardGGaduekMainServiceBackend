package org.cardGGaduekMainService.codef.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorListItem {

    private String clientType;
    private String code;
    private String loginType;
    private String countryCode;
    private String organization;
    private String businessType;
    private String message;
}
