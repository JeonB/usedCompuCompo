package thwjd.usedbook.entity;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class Member {
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min=2, message = "2글자 이상이어야합니다.")
    private String name;

    @NotBlank
    @Size(min=2, message = "2글자 이상이어야합니다.")
    private String password;

    @Size(min=2, message = "2글자 이상이어야합니다.")
    private String oldPassword;

    public Member(){

    }

    public Member(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
