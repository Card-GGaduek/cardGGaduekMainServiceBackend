package org.cardGGaduekMainService.codef.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodefAccountRegisterRequest {

    @JsonProperty("accountList")
    private List<AccountRequest> accountRequestList;


}
