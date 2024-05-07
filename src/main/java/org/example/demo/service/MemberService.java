package org.example.demo.service;

import org.example.demo.dto.BoardDTO;
import org.example.demo.dto.MemberJoinDTO;

public interface MemberService {
    static class MidExistException extends Exception {
        //ID 가 중복되면 안됨!! 예외발생하기 때문에 예외처리를 함

        public MidExistException(){}
        public MidExistException(String message) {
            super(message);
        }
    }
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException ;

}
