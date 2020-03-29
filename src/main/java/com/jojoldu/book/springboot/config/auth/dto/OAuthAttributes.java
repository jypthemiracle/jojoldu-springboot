package com.jojoldu.book.springboot.config.auth.dto;

import com.jojoldu.book.springboot.domain.user.Role;
import com.jojoldu.book.springboot.domain.user.User;
import java.util.Map;

public class OAuthAttributes {

  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name,
      String email, String picture) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  //OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야만 한다.
  public static OAuthAttributes of(String registrationId, String userNameAttributeName,
      Map<String, Object> attributes) {
    if ("naver".equals(registrationId)) {
      return ofNaver("id", attributes);
    }
    return ofGoogle(userNameAttributeName, attributes);
  }

  public static OAuthAttributes ofGoogle(String userNameAttributeName,
      Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("picture"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    return OAuthAttributes.builder()
        .name((String) response.get("name"))
        .email((String) response.get("email"))
        .picture((String) response.get("profile_image"))
        .attributes(response)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  public static OAuthAttributesBuilder builder() {
    return new OAuthAttributesBuilder();
  }

  //User 엔티티를 생성한다. OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때이다.
  //가입 권한을 기본으로 주기 위해서 role 빌더값에는 Role.GUEST를 사용한다.
  //OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스를 생성한다.
  public User toEntity() {
    return User.builder()
        .name(name)
        .email(email)
        .picture(picture)
        .role(Role.GUEST)
        .build();
  }

  public Map<String, Object> getAttributes() {
    return this.attributes;
  }

  public String getNameAttributeKey() {
    return this.nameAttributeKey;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPicture() {
    return this.picture;
  }

  public static class OAuthAttributesBuilder {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    OAuthAttributesBuilder() {
    }

    public OAuthAttributes.OAuthAttributesBuilder attributes(Map<String, Object> attributes) {
      this.attributes = attributes;
      return this;
    }

    public OAuthAttributes.OAuthAttributesBuilder nameAttributeKey(String nameAttributeKey) {
      this.nameAttributeKey = nameAttributeKey;
      return this;
    }

    public OAuthAttributes.OAuthAttributesBuilder name(String name) {
      this.name = name;
      return this;
    }

    public OAuthAttributes.OAuthAttributesBuilder email(String email) {
      this.email = email;
      return this;
    }

    public OAuthAttributes.OAuthAttributesBuilder picture(String picture) {
      this.picture = picture;
      return this;
    }

    public OAuthAttributes build() {
      return new OAuthAttributes(attributes, nameAttributeKey, name, email, picture);
    }

    public String toString() {
      return "OAuthAttributes.OAuthAttributesBuilder(attributes=" + this.attributes
          + ", nameAttributeKey=" + this.nameAttributeKey + ", name=" + this.name + ", email="
          + this.email + ", picture=" + this.picture + ")";
    }
  }
}
