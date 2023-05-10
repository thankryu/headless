package mmd.headless.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultResponse <T> {

    private T data;
    private String status;

    public ResultResponse(String status) {
        this.status = status;
    }
}