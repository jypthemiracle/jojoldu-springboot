package com.jojoldu.book.springboot.domain.user;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User extends BaseTimeEntity {

  public User() {}

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column
  private String picture;

  @Enumerated(EnumType.STRING)
  //JPA로 데이터베이스를 저장할 때 Enum 값을 어떤 형태로 저장할 지 결정할 수 있다.
  //기본값은 문자열이지만, 숫자로 저장되면 식별에 어려움이 있을 수 있으니 문자열로 저장하자.
  @Column(nullable = false)
  private Role role; //각 사용자의 권한을 관리할 Enum 클래스 Role이다.

  public User(String name, String email, String picture, Role role) {
    this.name = name;
    this.email = email;
    this.picture = picture;
    this.role = role;
  }

  public static UserBuilder builder() {
    return new UserBuilder();
  }

  public User update(String name, String picture) {
    this.name = name;
    this.picture = picture;
    return this;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getRoleKey() {
    return this.role.getKey();
  }

  public static class UserBuilder {

    private String name;
    private String email;
    private String picture;
    private Role role;

    UserBuilder() {}

    public UserBuilder name(String name) {
      this.name = name;
      return this;
    }

    public UserBuilder email(String email) {
      this.email = email;
      return this;
    }

    public UserBuilder picture(String picture) {
      this.picture = picture;
      return this;
    }

    public UserBuilder role(Role role) {
      this.role = role;
      return this;
    }

    public User build() {
      return new User(name, email, picture, role);
    }

    public String toString() {
      return "User.UserBuilder(name=" + this.name + ", email=" + this.email + ", picture="
          + this.picture + ", role=" + this.role + ")";
    }
  }
}