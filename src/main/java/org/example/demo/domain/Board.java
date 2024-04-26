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
}
