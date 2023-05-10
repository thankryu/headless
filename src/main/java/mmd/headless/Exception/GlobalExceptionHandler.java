package mmd.headless.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 파라미터를 받았을 때
     * @param ex
     * @return
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String, Object> userNotFound(ValidationException ex) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("msg", ex.getMsg());
        if(!Objects.isNull(ex.getErrorMap())){
            resultMap.put("error", ex.getErrorMap());
        }
        return resultMap;
    }
}
