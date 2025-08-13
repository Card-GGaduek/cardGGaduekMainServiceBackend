package org.cardGGaduekMainService.codef.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CodefEndPoint {

    CODEF_OAUTH_TOKEN("https://oauth.codef.io/oauth/token"),
    CODEF_REGISTER_ACCOUNT("https://development.codef.io/v1/account/create"),
    CODEF_ADD_ACCOUNT("https://development.codef.io/v1/account/add"),
    CODEF_MY_CARD_LIST("https://development.codef.io/v1/kr/card/p/account/card-list"),
    CODEF_MY_CARD_DETAIL_LIST("https://development.codef.io/v1/kr/card/p/account/result-check-list");

    private final String path;



}
