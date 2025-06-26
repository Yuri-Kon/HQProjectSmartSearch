package bighomework.web.front.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register_Company {
    private String username;
    private String password;
    private String usertype;
    private String company_name;
    private String company_key;
    private String company_tele;
}
