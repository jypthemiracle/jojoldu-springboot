package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import java.time.LocalDateTime;
import org.junit.*;
import org.junit.runner.RunWith;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;

//postsRepository의 save, findAll 기능을 테스트한다.
@RunWith(SpringRunner.class)
//H2 데이터베이스를 자동으로 실행해주는 역할을 기능한다.
//실행하여 쿼리를 불러오면 id bigint generated by default as identity 라고 8비트 기본값이 지정됨을 알 수 있다.
//spring.jpa.show_sql=true를 지정하면 쿼리를 볼 수 있다.
//spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect라고 지정하면 mySQL의 쿼리 지정이 되며 null 값이 없어진다.
@SpringBootTest
public class PostsRepositoryTest {

  @Autowired
  PostsRepository postsRepository;

  @Autowired
  private PostsService postsService;

  //JUnit에서 단위 테스트가 끝날 때마다 테스트 간 데이터 침범을 막기 위해 사용한다.
//  @After
//  public void cleanup() {
//    postsRepository.deleteAll();
//  }

  @Test
  public void 게시물저장_불러오기() {
    //given
    String title = "테스트 게시글";
    String content = "테스트 본문";

    //테이블 posts에 insert/update 쿼리를 실행한다. id 값이 있다면 update, 없다면 insert 쿼리가 수행된다.
    postsRepository.save(Posts.builder()
        .title(title)
        .content(content)
        .author("hophfg@gmail.com")
        .build()
    );

    //when
    List<Posts> postsList = postsRepository.findAll(); //모든 데이터를 조회하는 메소드이다.

    //then
    Posts posts = postsList.get(0); //0번 인덱스의 가장 먼저 글을 하나 뽑아본다.
    assertThat(posts.getTitle()).isEqualTo(title);
    assertThat(posts.getContent()).isEqualTo(content);
  }

  @Test
  public void BaseTimeEntity가_등록된다() {
    //given
    LocalDateTime now = LocalDateTime.of(2020, 02, 24, 10, 33, 25); //LocalDateTime.of를 하면 연월일시분초를 입력할 수 있다.
    postsRepository.save(Posts.builder()
      .title("title")
      .content("content")
      .author("hophfg@gmail.com")
      .build()
    );

    //when
    List<Posts> postsList = postsRepository.findAll();
    //then
    Posts posts = postsList.get(0);
    postsService.update(posts.getId(), PostsUpdateRequestDto.builder()
        .title("new title")
        .content("new content")
        .build()
    );

    System.out.println(">>>>> getCreatedTime " + posts.getCreatedDate());
    System.out.println(">>>>> getModifiedDate " + posts.getModifiedDate());

    assertThat(posts.getCreatedDate()).isAfter(now); //The isAfter() method of LocalDate class in Java checks if this date is after the specified date.
    assertThat(posts.getModifiedDate()).isAfter(now);
  }
}