package org.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private Long rno;       //자동생성됨

    @NotNull
    private Long bno;
    @NotEmpty       //스페이스 자체도 안됨!
    private String replyText;
    @NotEmpty
    private String replyer;

    private LocalDateTime regDate,modDate;

}
