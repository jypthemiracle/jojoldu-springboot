package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@RequiredArgsConstructor 어노테이션은 final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만들어 준다.
//@Autowired를 사용하지 않는 또 다른 의존성 주입의 방법이다. 해당 클래스의 의존성 관계가 변경되어도 생성자 코드를 작성하지 않아도 된다.
@RequiredArgsConstructor
//@RestController의 주용도는 Json/Xml 형태로 객체 데이터를 반환하는 것이다.
@RestController
public class PostsApiController {

  private final PostsService postsService;

  //POST 명령으로 저장하기가 가능하다.
  @PostMapping("/api/v1/posts") //HTTP의 PUT 메소드를 활용하여 전체 값을 대입한다. 초기에만 실행한다.
  public Long save(@RequestBody PostsSaveRequestDto requestDto) {
    return postsService.save(requestDto);
  }

  //POST 또는 PUT 명령으로 게시물 수정하기 기능을 추가한다.
  @PutMapping("/api/v1/posts/{id}")
  public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
    return postsService.update(id, requestDto);
  }

  //GET 명령으로 게시물 조회하기 기능을 추가한다.
  @GetMapping("/api/v1/posts/{id}")
  public PostsResponseDto findById (@PathVariable Long id) {
    return postsService.findById(id);
  }
}
