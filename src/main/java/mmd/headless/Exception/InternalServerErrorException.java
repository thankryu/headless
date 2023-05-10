package mmd.headless.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalServerErrorException extends RuntimeException{
    private String msg;

    public InternalServerErrorException(String msg){
        this.msg = msg;
    }
}
