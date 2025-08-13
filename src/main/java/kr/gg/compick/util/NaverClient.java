package kr.gg.compick.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.*;
import org.springframework.web.client.RestClient;

@Component
public class NaverClient {
  @Value("${app.oauth.naver.client-id}") String clientId;
  @Value("${app.oauth.naver.client-secret}") String clientSecret;
  @Value("${app.oauth.naver.redirect-uri}") String redirectUri;

  private final RestClient http = RestClient.create();

  public OAuthToken exchangeCode(String code, String state){
    MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
    form.add("grant_type","authorization_code");
    form.add("client_id", clientId);
    form.add("client_secret", clientSecret);
    form.add("redirect_uri", redirectUri);
    form.add("code", code);
    form.add("state", state);
    return http.post().uri("https://nid.naver.com/oauth2.0/token")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .body(form).retrieve().body(OAuthToken.class);
  }

  public NaverProfile me(String accessToken){
    return http.get().uri("https://openapi.naver.com/v1/nid/me")
      .header(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken)
      .retrieve().body(NaverProfile.class);
  }
}
