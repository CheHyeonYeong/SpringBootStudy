package org.example.demo.controller.advice;



import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.example.demo.dto.PageRequestDTO;
import org.example.demo.dto.PageResponseDTO;
import org.example.demo.dto.ReplyDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handlerBindException(BindException e) {
        log.error(e);
        Map<String, String> errorMap = new HashMap<>();
        if (e.hasErrors()){
            BindingResult bindingResult = e.getBindingResult();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getCode());
            });
        }
        return ResponseEntity.badRequest().body(errorMap);      //기존의 417번 에러를 400 에러로 바꿔서 보내준다.
    }

    //예외 관련 항목들은
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String,String>> handlerFKException(Exception e){
        log.info(e);
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("time", ""+System.currentTimeMillis());
        errorMap.put("msg", e.getMessage());

        return ResponseEntity.badRequest().body(errorMap);
    }
    //NoSuchElement
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String,String>> handlerNoSuchElement(Exception e){
        log.info(e);
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("time", ""+System.currentTimeMillis());
        errorMap.put("msg", "No Such element Exception");

        return ResponseEntity.badRequest().body(errorMap);
    }




}
