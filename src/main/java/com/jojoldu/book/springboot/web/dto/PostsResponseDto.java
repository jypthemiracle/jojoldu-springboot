package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.posts.Posts;

//PostsResponseDto는 Entity의 필드 중 일부만을 사용한다. 생성자로 Entity를 받아 필드에 값을 넣는다.

public class PostsResponseDto {

  private Long id;
  private String title;
  private String content;
  private String author;

  public PostsResponseDto(Posts entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
  }
}