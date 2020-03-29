package com.jojoldu.book.springboot.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //랜덤포트를 실행한다.
public class PostsApiControllerTest {

  @LocalServerPort //로컬 내부서버의 포트를 가져온다.
  private int port;

  @Autowired
  private TestRestTemplate restTemplate; //REST API 응답을 동기방식으로 처리하는 RestTemplate의 테스트이다. 서블릿 컨테이너가 존재한다.

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Before
  public void setup() {
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  @After
  public void tearDown() throws Exception {
    postsRepository.deleteAll();
  }

  String title = "title";
  String content = "content";
  String author = "author";

  @Test
  @WithMockUser(roles="USER")
  public void Posts가_등록된다() throws Exception {
    //given
    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                    .title(title)
                                                    .content(content)
                                                    .author(author)
                                                    .build();

    String url = "http://localhost:" + port + "api/v1/posts";

    //when
    mvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk());

    //then
    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(title);
    assertThat(all.get(0).getContent()).isEqualTo(content);

//    //when
//    ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
//    //ResponseEntity는 HttpEntity를 상속받음으로써 HttpHeader와 body를 가질 수 있다.
//    //restTemplate.postForEntity은 POST 요청을 보내고 결과로 ResponseEntity로 반환받는다.
//
//    //then
//    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    assertThat(responseEntity.getBody()).isGreaterThan(0L); //Long으로 넘어오는 바디가 0L(0)보다 큰가?
//
//    List<Posts> all = postsRepository.findAll();
//    assertThat(all.get(0).getTitle()).isEqualTo(title);
//    assertThat(all.get(0).getContent()).isEqualTo(content);
//    assertThat(all.get(0).getAuthor()).isEqualTo(author);
  }

  @Test
  //인증된 모의(가짜) 사용자를 만들어서 사용한다. roles에 권한을 추가할 수 있다.
  //즉, 이 어노테이션으로 인해 ROLE_USER 권한을 가진 사용자처럼 쓸 수 있다. 단, MockMvc에서만 작동한다.
  @WithMockUser(roles="USER")
  public void Posts가_수정된다() throws Exception {
    //given
    Posts savedPosts = postsRepository.save(Posts.builder()
        .title(title)
        .content(content)
        .author(author)
        .build()
    );

    Long updateId = savedPosts.getId();
    String expectedTitle = "title2";
    String expectedContent = "content2";

    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
        .title(expectedTitle)
        .content(expectedContent)
        .build();

    String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

    HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

    //when
    mvc.perform(put(url)
    .contentType(MediaType.APPLICATION_JSON_UTF8)
    .content(new ObjectMapper().writeValueAsString(requestDto)))
    .andExpect(status().isOk());

    //then
    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
    assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

//    //when
//    ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Long.class);
//
//    //then
//    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    assertThat(responseEntity.getBody()).isGreaterThan(0L);
//    List<Posts> all = postsRepository.findAll();
//    assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
//    assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
  }
}
