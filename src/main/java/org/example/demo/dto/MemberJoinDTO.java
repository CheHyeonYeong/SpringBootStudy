package org.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data       //getter, setter, toString이 한 번에 만들어짐
public class MemberJoinDTO {

    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;
    
    

}
