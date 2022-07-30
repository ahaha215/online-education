package com.lt.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName GiliException
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{
    private Integer code;
    private String msg;
}
