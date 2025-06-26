package bighomework.web.front.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register_Teacher {
    private String username;
    private String password;
    private String usertype;
    private String teacher_name;
    private String teacher_position;
    private String teacher_email;
    private String teacher_tele;
    private String teacher_field;

}
