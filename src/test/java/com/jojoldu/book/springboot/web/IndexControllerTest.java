package com.jojoldu.book.springboot.web;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {
  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void 메인페이지가_로딩된다() {
    //when
    String body = this.testRestTemplate.getForObject("/", String.class);
    //getForObject: Retrieve a representation by doing a GET on the URI template.
    //then
    assertThat(body).isNotNull();
    //TestRestTemplate으로 RestTemplate를 Test해보았을 때, 넘겨주는 body 문자열에 우리가 기대하는 값이 있으면 된다.
  }
}