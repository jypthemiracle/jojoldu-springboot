package com.jojoldu.book.springboot.service.posts;

import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service //이 클래스는 서비스라고 선언해줄 수 있다.
public class PostsService {
  private final PostsRepository postsRepository;

  //트랜잭션은 데이터베이스 상태를 변환시키는 하나의 논리적인 기능의 작업 단위이다.
  //Transaction은 2개 이상의 쿼리를 하나의 커넥션으로 묶어 DB에 전송하고, 이 과정에서 에러가 발생할 경우 자동으로 모든 과정을 원래대로 되돌려 놓는다.
  //이러한 과정을 구현하기 위해 Transaction은 하나 이상의 쿼리를 처리할 때 동일한 Connection 객체를 공유하도록 한다.
  @Transactional
  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }
}