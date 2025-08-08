package kr.gg.compick.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ResponseData<T> success(){
        return new ResponseData<>(200, "성공", null);
    }

    public static <T> ResponseData<T> success(T data){
        return new ResponseData<>(200, "성공", data);
    }

    public static <T> ResponseData<T> success(String message, T data){
        return new ResponseData<>(200, message, data);
    }

    public static <T> ResponseData<T> error(int code, String message){
        return new ResponseData<>(code, message, null);
    }
}
