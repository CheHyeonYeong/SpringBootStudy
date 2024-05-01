package org.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Reply", indexes = {
        @Index(name = "idx-reply_board_bno", columnList = "board_bno")
})

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor      //참조하는 객체를 사용하지 않게 하기 위해서 tostring의 exclude로 board를 뺀 것이다.
@ToString(exclude = "board")        // 멤버들이 객체로 쭉 나오는데 board라는 녀석을 빼달라
public class Reply extends BaseEntity{      //등록 시간 설정이 가능

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //auto increament 와 동일하다
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)      //천천히, 느리게, 즉각 작업을 하지 않는다, 다대일 관계 로 구성됨(연관관계시, fetch = FetchType.LAZY로 구성)
    private Board board;                    //board를 구분할 수 있는 pk인 bno가 들어감

    private String replyText;
    private String replyer;

    public void setBoard(Long bno) {
        this.board = Board.builder().bno(bno).build();
    }
    public void changeText(String text){
        this.replyText = text;
    }

}
