package com.jojoldu.book.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class) //테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
//여기서는 SpringRunner라는 스프링 실행자를 실행하는데, 이는 스프링 부트 테스트와 JUnit 사이의 연결자 역할을 한다.
@WebMvcTest //WebMvc를 테스트하겠다고 선언하는 어노테이션.
public class HelloControllerTest {
  @Autowired //Bean을 주입하는 역할을 수행한다.
  private MockMvc mvc; //GET, POST 등 웹 API를 테스트할 때 사용된다. 스프링 MVC 테스트의 시작점이다.

  @Test
  public void hello가_리턴된다() throws Exception {
    String hello = "hello";
    mvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다. 체이닝이 지원된다.
        .andExpect(status().isOk()) //mvc.perform의 결과를 검증하고, HTTP header의 status를 검증한다. 200 OK인지 검증한다.
        .andExpect(content().string(hello)); //mvc.perform의 결과를 검증한다. Controller가 hello를 리턴하는 지 검증한다.
  }
}
