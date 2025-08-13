package kr.gg.compick.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.*;
import org.springframework.web.client.RestClient;

@Component
public class KakaoClient {
  @Value("${app.oauth.kakao.client-id}") String clientId;
  @Value("${app.oauth.kakao.client-secret:}") String clientSecret;
  @Value("${app.oauth.kakao.redirect-uri}") String redirectUri;

  private final RestClient http = RestClient.create();

  public OAuthToken exchangeCode(String code){
    MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
    form.add("grant_type","authorization_code");
    form.add("client_id", clientId);
    if(!clientSecret.isEmpty()) form.add("client_secret", clientSecret);
    form.add("redirect_uri", redirectUri);
    form.add("code", code);
    return http.post().uri("https://kauth.kakao.com/oauth/token")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .body(form).retrieve().body(OAuthToken.class);
  }

  public KakaoProfile me(String accessToken){
    return http.get().uri("https://kapi.kakao.com/v2/user/me")
      .header(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken)
      .retrieve().body(KakaoProfile.class);
  }
}
