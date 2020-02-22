package com.jojoldu.book.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러를 JSON으로 반환하는 컨트롤러로 만들어줍니다.
public class HelloController {
  @GetMapping("/hello") //HTTP Method인 GET의 요청을 받을 수 있는 API를 만듭니다.
  public String hello() {
    return "hello";
  }
}
