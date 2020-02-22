package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@RequiredArgsConstructor 어노테이션은 final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만들어 준다.
//@Autowired를 사용하지 않는 또 다른 의존성 주입의 방법이다. 해당 클래스의 의존성 관계가 변경되어도 생성자 코드를 작성하지 않아도 된다.
@RequiredArgsConstructor
//@RestController의 주용도는 Json/Xml 형태로 객체 데이터를 반환하는 것이다.
@RestController
public class PostsApiController {

  private final PostsService postsService;

  @PostMapping("/api/v1/posts") //HTTP의 PUT 메소드를 활용하여 전체 값을 대입한다. 초기에만 실행한다.
  public Long save(@RequestBody PostsSaveRequestDto requestDto) {
    return postsService.save(requestDto);
  }
}
