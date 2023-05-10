package mmd.headless.util;

import mmd.headless.Exception.ValidationException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class InValidErrorHandlingUtil {
    /**
     * @Vaild 사용시 에러 핸들링
     * @param errors
     */
    public void errorHandler(Errors errors) {
        if(errors.hasErrors()){
            Map<String, String> validatorResult = validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                validatorResult.put(key, validatorResult.get(key));
            }
            throw new ValidationException("파라미터 오류", validatorResult);
        }
    }

    private Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = error.getField();
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }
}