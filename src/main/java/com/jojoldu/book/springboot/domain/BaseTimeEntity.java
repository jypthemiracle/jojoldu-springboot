package com.jojoldu.book.springboot.domain;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import lombok.Getter;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter

//JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우, createdDate나 modifiedDate도 칼럼으로 인식하도록 한다.
@MappedSuperclass

//BaseTimeEntity 클래스에 Auditing 기능을 포함시키도록 한다.
@EntityListeners(AuditingEntityListener.class)

//BaseTimeEntity 클래스는 모든 Entity의 상위 클래스가 되어 Entity들의 createdDate, modifiedDate를 자동으로 관리한다.
public abstract class BaseTimeEntity {

  @CreatedDate
  //Entity가 생성되어 저장될 때 시간이 자동 저장된다.
  private LocalDateTime createdDate;
  @LastModifiedDate
  //조회한 Entity의 값을 변경할 때 시간이 자동 저장된다.
  private LocalDateTime modifiedDate;
}
