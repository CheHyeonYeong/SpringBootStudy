package org.example.demo.dto;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.demo.domain.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//스프링 시큐리티에서는 UserDetails라는 인터페이스를 사용합니다
//때문에 스프링 시큐리티에서 UserDetail의 구현체인 User을 상속 받아서 구현

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;

    public MemberSecurityDTO(String username, String password, String email, boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {      //GrantedAuthority는 security에서 받아온 것

        super(username, password, authorities);

        //생성자는 상속되지 않으므로 persistance에 적용하려면 이렇게 생성해줘야 한다
        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.del = del;
        this.social = social;
    }



}
