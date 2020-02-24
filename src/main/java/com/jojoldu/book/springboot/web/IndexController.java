package com.jojoldu.book.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  //머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경와 뒤의 파일 확장자는 자동으로 지정된다.
  @GetMapping("/") //앞의 경로
  public String index() {
    return "index"; //파일 확장자. 이는 View Resolver가 처리한다.
    //View Resolver는 URL 요청의 결과를 전달할 타입과 값을 지정하는 관리자 격으로 생각해볼 수 있다.
  }
}