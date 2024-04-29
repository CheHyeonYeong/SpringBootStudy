package org.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {
    //시간 설정 - 등록, 수정과 관련된 시간 설정
    @CreatedDate    //생성 시간 설정
    @Column(name = "regdate", updatable = false) //이름은 regdate, update는 불가능
    private LocalDateTime regDate;

    @LastModifiedDate       //마지막 수정 날짜
    @Column(name = "moddate")
    private LocalDateTime modDate;


}
