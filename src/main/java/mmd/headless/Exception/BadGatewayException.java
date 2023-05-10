package mmd.headless.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadGatewayException extends RuntimeException{
    private String msg;
    private Exception errorMsg;

    public BadGatewayException(String msg){
        this.msg = msg;
    }

    public BadGatewayException(String msg, Exception errorMsg){
        this.msg = msg;
        this.errorMsg = errorMsg;
    }
}
