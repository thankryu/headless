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

    /**
     * 인증 실패 시
     * @param ex
     * @return
     */
    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody Map<String, Object> ex(UnAuthorizedException ex) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("msg", ex.getMsg());
        return resultMap;
    }

    /**
     * 서버내 로직 오류 일 때
     * @param ex
     * @return
     */
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody Map<String, Object> internalServerError(InternalServerErrorException ex) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("msg", ex.getMsg());
        return resultMap;
    }

    /**
     * 외부와 통신 오류가 발생했을 때
     * @param ex
     * @return
     */
    @ExceptionHandler(BadGatewayException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public @ResponseBody Map<String, Object> exApiFail(BadGatewayException ex) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("msg", ex.getMsg());
        resultMap.put("error", ex.getErrorMsg());
        return resultMap;
    }
}
