package com.jojoldu.book.springboot.domain.user;

public enum Role {
  GUEST("ROLE_GUEST", "손님"), //스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야만 한다.
  USER("ROLE_USER", "일반 사용자");

  private final String key;
  private final String title;

  private Role(String key, String title) { //ROLE 생성자
    this.key = key;
    this.title = title;
  }

  public String getKey() {
    return this.key;
  }

  public String getTitle() {
    return this.title;
  }
}
