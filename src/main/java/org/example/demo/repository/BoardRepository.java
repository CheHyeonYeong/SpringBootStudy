package org.example.demo.repository;

import org.example.demo.domain.Board;
import org.example.demo.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch { //type = Board, Boad bno의 type인 id
    //JPA로 DB 관련 작업을 수행하기 위한 id 값
    //하나면 OPTIONAL, 여러개면 LIST -> 쿼리메서드 방식
    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);

    //@Query 어노테이션에서 사용하는 구문은 JPQL을 사용
    //JPQL SQL과 유사하게  JPA에서 사용하는 쿼리 언어
    //@Query를 이용하는 경우
    //1. 조인과 같이 복잡한 쿼리를 실행하려고 할 떄
    //2. 원하는 속성만 추출해서 Object[] 로 처리하거나 DTO 로 처리가능
    //3. 속성 값 중 nativeQuery 속성 값을 true로 지정하면 SQL구문으로 사용이 가능함.
    @Query("select b FROM Board b where b.title like concat('%',:keyword,'%')")
    Page<Board> findKeyword(String keyword, Pageable pageable);

    @Query(value = "select now()", nativeQuery = true)
    String getTime();


}
