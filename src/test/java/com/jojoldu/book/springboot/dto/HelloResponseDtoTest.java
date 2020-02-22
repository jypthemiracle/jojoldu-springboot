package com.jojoldu.book.springboot.dto;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import static org.assertj.core.api.Assertions.assertThat; //테스트 라이브러리로 assertj를 사용한다.
import org.junit.Test;
//JUnit과 비교해서 assertj의 장점은 무엇일까?
//1. JUnit을 활용하면 CoreMatchers와 같은 추가적인 라이브러리가 필요하므로 번거롭다. (예: is())
//2. 자동완성이 조금 더 확실하게 지원된다. IDE에서는 CoreMatchers와 같은 Matcher 라이브러리의 자동완성이 약하다.

public class HelloResponseDtoTest {
  @Test
  public void 롬복_기능_테스트() {
    //given
    String name = "test";
    int amount = 1000;

    //when
    HelloResponseDto dto = new HelloResponseDto(name, amount);

    //then
    assertThat(dto.getName()).isEqualTo(name);
    assertThat(dto.getAmount()).isEqualTo(amount);
  }
}
