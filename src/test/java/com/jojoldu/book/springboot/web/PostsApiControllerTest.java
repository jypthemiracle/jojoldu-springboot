package com.jojoldu.book.springboot.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //랜덤포트를 실행한다.
public class PostsApiControllerTest {

  @LocalServerPort //로컬 내부서버의 포트를 가져온다.
  private int port;

  @Autowired
  private TestRestTemplate restTemplate; //REST API 응답을 동기방식으로 처리하는 RestTemplate의 테스트이다. 서블릿 컨테이너가 존재한다.

  @Autowired
  private PostsRepository postsRepository;

  @After
  public void tearDown() throws Exception {
    postsRepository.deleteAll();
  }

  @Test
  public void Posts가_등록된다() throws Exception {
    //given
    String title = "title";
    String content = "content";
    String author = "author";
    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                    .title(title)
                                                    .content(content)
                                                    .author(author)
                                                    .build();
    String url = "http://localhost:" + port + "api/v1/posts";

    //when
    ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
    //ResponseEntity는 HttpEntity를 상속받음으로써 HttpHeader와 body를 가질 수 있다.
    //restTemplate.postForEntity은 POST 요청을 보내고 결과로 ResponseEntity로 반환받는다.

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L); //Long으로 넘어오는 바디가 0L(0)보다 큰가?

    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(title);
    assertThat(all.get(0).getContent()).isEqualTo(content);
    assertThat(all.get(0).getAuthor()).isEqualTo(author);
  }
}
