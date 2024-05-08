package org.example.demo.repository;

import jakarta.transaction.Transactional;
import org.example.demo.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, String> {

    //소셜 로그인을 사용하지 않은 사용자의 권한 로딩
    @EntityGraph(attributePaths = "roleSet")        //조인을 위해 사용한 어노테이션
    @Query("select m from Member m where m.mid = :mid and m.social = false ")       //social login 사용자가 아닐때
    Optional<Member> getWithRoles(String mid);

    @EntityGraph(attributePaths = "roleSet")
    Optional<Member> findByEmail(String email);

    @Modifying      //이 어노테이션을 사용하면 @Query에서 DML(insert/update/delete) 처리를 가능하게 함
    @Transactional
    @Query("update Member m set m.mpw =:mpw where  m.mid = :mid")
    void updatePassword(@Param("mpw") String password, @Param("mid") String mid);


}
