package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserRepository userRepository;
  private final HttpSession httpSession;

  public CustomOAuth2UserService(UserRepository userRepository, HttpSession httpSession) {
    this.userRepository = userRepository;
    this.httpSession = httpSession;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) {
    OAuth2UserService delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    //현재 진행 중인 로그인의 서비스를 구분하는 코드입니다.
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    //유저 로그인 시 사용하는 Key field와 같은 값입니다.
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
        .getUserInfoEndpoint().getUserNameAttributeName();
    //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스입니다. 일종의 DTO입니다.
    OAuthAttributes attributes = OAuthAttributes
        .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

    User user = saveOrUpdate(attributes);
    //세션에 사용자 정보를 저장하기 위한 DTO 클래스로서, User 클래스를 사용하지 않고 새로 만들어서 사용한다.
    httpSession.setAttribute("user", new SessionUser(user));
    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
        attributes.getAttributes(),
        attributes.getNameAttributeKey());
  }

  private User saveOrUpdate(OAuthAttributes attributes) {
    //구글 사용자 정보가 업데이트되었을 경우 update 기능이 실현된다.
    //사용자의 이름이나 프로필 사진이 변경되면 User 엔티티에도 반영된다.
    User user = userRepository.findByEmail(attributes.getEmail())
        .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
        .orElse(attributes.toEntity());

    return userRepository.save(user);
  }
}
