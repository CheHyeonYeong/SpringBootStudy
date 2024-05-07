package org.example.demo.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity{

    @Id
    private String mid;
    private String mpw;
    private String email;

    private boolean del;
    //열거형 처리... roleSet
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();      //집합을 씀 -> 중복을 제거해야 해서

    //social login
    private boolean social;

    //패스워드 변경
    public void chagePassword(String mpw){ this.mpw = mpw; }
    public void changeEmail(String email) { this.email = email; }
    //삭제 여부 변경 가능
    public void changeDel(boolean del) { this.del = del; }

    //addRole 역할 추가
    public void addRole(MemberRole role) { this.roleSet.add(role); }
}
