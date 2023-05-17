package mmd.headless.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mmd.headless.enums.JoinType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class MemberRequest {

    @Schema(title = "사용자 이름", example = "thankryu")
    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String username;

    @Schema(title = "사용자 이메일", example = "thankryu@gmail.com")
    @NotEmpty(message = "이메일은 필수 입니다")
    @Email
    private String email;

    @Schema(title = "비밀번호", example = "1q2w3e4r5t")
    @NotEmpty(message = "비밀번호는 필수입니다")
    private String password;

    @Schema(title = "비밀번호", example = "010-0000-0000")
    @NotEmpty(message = "전화번호는 필수입니다")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx")
    private String phone;

    private JoinType joinType;

    @Builder
    public MemberRequest(String username, String email, String password, String phone, JoinType joinType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.joinType = joinType;
    }
}