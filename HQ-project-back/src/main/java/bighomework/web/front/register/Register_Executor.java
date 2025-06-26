package bighomework.web.front.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register_Executor {
    private String username;
    private String password;
    private String usertype;
    private String exe_id;
    private String exe_name;
    private String company_key;
}

