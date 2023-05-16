package mmd.headless.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenFailDto {
    String code;
    String msg;

    public TokenFailDto(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}