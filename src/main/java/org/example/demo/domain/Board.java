package org.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity     //엔티티 선언
@Getter
@Builder    //Setter 대신 Builder로 넣고 싶다.
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity{
    @Id     //pk 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    @Column(nullable = false,length = 500)
    private String title;
    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    //엔티티 내의 변경가능한 타이틀과 content 값을 수정하는 메소드
    //수정하기 위한 방식을 따로 만들었다. 다른 방식을 넣어도 된다. id가 바뀌지 않으면 된다
    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
}
