package mmd.headless.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnAuthorizedException extends RuntimeException {
    private String msg;

    public UnAuthorizedException(String msg){
        this.msg = msg;
    }
}
