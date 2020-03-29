package com.jojoldu.book.springboot.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email); //findByEmail: 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하는 메소드다.
  // 아직 가입을 하지 않아 이메일이 없을 수도 있으므로 null 값을 처리하고자 Optional을 활용했다.
}