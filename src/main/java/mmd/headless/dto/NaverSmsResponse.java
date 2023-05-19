package mmd.headless.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverSmsResponse {
    public String statusCode;
    public String requestTime;
    public String statusName;
    public String requestId;
}