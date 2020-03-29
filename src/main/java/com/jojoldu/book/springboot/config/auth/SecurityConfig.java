package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정을 활성화시킨다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomOAuth2UserService customOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().headers().frameOptions().disable() //h2-console 화면을 사용하기 위해 해당 옵션을 disable한다.
    .and().authorizeRequests().antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
        //authorizeRequest는 URL별 권한 관리를 설정하는 옵션의 시작점이다. authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다.
        //antMatchers는 권한 관리 대상을 지정하는 옵션이다. URL, HTTP 메소드 별로 관리가 가능하다.
        //"/" 등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었다.
        //POST 메소드이면서 '/api/v1/**' 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 했다.
        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
        .anyRequest().authenticated()
        //설정된 값들 이외의 나머지 URL들은 인증된 사용자들에게만 허용하도록 했다.
    .and().logout().logoutSuccessUrl("/")
        //로그아웃 기능에 대한 여러 설정의 진입점으로, 로그아웃 성공 시 / 주소로 이동한다.
    .and().oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
        //oauth2Login은 OAuth 2 로그인 기능에 대한 여러 설정의 진입점이다.
        //userInfoEndpoint는 OAuth 2 로그인 성공 이후 사용자 정보를 가져올 떄의 설정들을 담당한다.
        //userService는 소셜 로그인 성공 이 UserService 인터페이스의 구현체를 등록한다. 리소스 서버에서 사용자 정보를 가져온 상태에서 추가 진행 기능을 명시할 수 있다.
  }

}
