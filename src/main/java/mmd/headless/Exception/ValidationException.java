package mmd.headless.Exception;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ValidationException extends RuntimeException {
    private String msg;
    private Map<String, String > errorMap;

    public ValidationException(String msg){
        this.msg = msg;
    }

    public ValidationException(String msg, Map<String, String> errorMap){
        this.msg = msg;
        if (errorMap != null) {
            this.errorMap = errorMap;
        } else {
            this.errorMap = new HashMap<>();
        }
    }
}