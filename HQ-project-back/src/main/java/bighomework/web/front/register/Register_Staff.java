package bighomework.web.front.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register_Staff {
    private String username;
    private String password;
    private String usertype;
    private String company_key;
}
