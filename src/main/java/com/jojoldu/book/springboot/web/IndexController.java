package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor //private field에서만 생성자를 필수로 만든다.
@Controller
public class IndexController {
  //머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경와 뒤의 파일 확장자는 자동으로 지정된다.
  private final PostsService postsService;
  private final HttpSession httpSession;

  @GetMapping("/") //앞의 경로
  public String index(Model model) {
    model.addAttribute("posts", postsService.findAllDesc());
    SessionUser user = (SessionUser) httpSession.getAttribute("user");
    if (user != null) {
      model.addAttribute("userName", user.getName());
    }
    return "index"; //파일 확장자. 이는 View Resolver가 처리한다.
    //View Resolver는 URL 요청의 결과를 전달할 타입과 값을 지정하는 관리자 격으로 생각해볼 수 있다.
  }
  @GetMapping("/posts/save")
  public String postsSave() {
    return "posts-save";
  }

  @GetMapping("/posts/update/{Id}")
  public String postsUpdate(@PathVariable Long Id, Model model) {
    PostsResponseDto responseDto = postsService.findById(Id);
    model.addAttribute("post", responseDto);
    return "posts-update";
  }
}