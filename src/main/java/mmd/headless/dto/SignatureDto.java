package mmd.headless.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignatureDto {
    String url; // include query string
    String method;
    String timestamp; // current timestamp(epoch)

    public SignatureDto(){};

    @Builder
    public SignatureDto(String url, String method, String timestamp) {
        this.url = url;
        this.method = method;
        this.timestamp = timestamp;
    }
}