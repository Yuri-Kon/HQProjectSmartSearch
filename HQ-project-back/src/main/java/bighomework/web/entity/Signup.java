package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Signup {
    private int signup_id;
    private int stu_id;
    private String course_id;
    private int signup_state;
}
