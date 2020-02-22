package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
  //보통 ibatis나 MyBatis에서 Dao라고 불리는 DB Layer 접근자이다.
  //JPA에서는 Repository라고 부르며 인터페이스로 생성한다.
  //단순히 인터페이스를 생성하고, JpaRepository<Entity 클래스, PK 타입>을 상속하면 기본적인 CRUD 메소드가 구축된다.
  //Entity 클래스와 기본 Entity Repository는 함께 위치하여야 한다. Entity 클래스는 Repo 없이는 동작하지 못한다.
}
