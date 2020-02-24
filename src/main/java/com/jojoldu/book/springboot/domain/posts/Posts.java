package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Entity 클래스에서는 절대 Setter 메소드를 사용하지 않는다. 필요할 경우 객체지향에 부합하는 방식으로 설계하여 가져온다.
@Getter //클래스 내 모든 필드의 Getter 메소드를 자동생성
@NoArgsConstructor //기본 생성자 자동 추가
@Entity //테이블과 링크될 클래스 임을 나타낸다. 기본값으로 클래스의 카멜케이스 이름이 테이블에서는 언더스코어 네이밍으로 바뀐다.

//롬복은 필수 어노테이션은 아니므로 주요 어노테이션인 @Entity를 더욱 가까이 둔다. Kotlin 등 전환 시 롬복 제거에 유리하다.
//Posts 클래스는 실제 DB 테이블과 매칭될 클래스이다. 이를 보여주기 위해 @Entity라는 표현을 사용했다.
//JPA를 사용하면 실제 SQL 쿼리를 날리는 것이 아니라 이 클래스를 객체지향 방식으로 수정하면서 작업할 수 있다.
public class Posts extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //Primary Key, 즉 데이터의 고유성을 표기할 키값의 생성 규칙을 나타낸다. auto_increment 방식이 도입되었다.
  private Long Id;

  @Column(length = 500, nullable = false)
  //테이블의 칼럼을 나타내지만, 굳이 선언하지 않더라도 모든 필드가 칼럼화가 된다.
  //기본값 외에 추가로 변경이 필요한 옵션이 있을 경우 사용한다.
  //문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리거나 타입을 TEXT로 변경하고 싶을 때 사용 가능하.
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  private String author;

  //해당 클래스의 빌더 패턴 클래스를 생성
  //생성자 선언에 선언 시 생성자에 포함된 필드만 빌더에 포함
  @Builder
  public Posts(String title, String content, String author) {
    this.title = title;
    this.content = content;
    this.author = author;
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
