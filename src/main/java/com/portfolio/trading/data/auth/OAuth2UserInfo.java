package com.portfolio.trading.data.auth;

import java.util.Map;

public interface OAuth2UserInfo {

    Map<String, Object> getAttributes();

    String getEmail();

    String getName();

    String getProviderId();

    String getProvider();

}
