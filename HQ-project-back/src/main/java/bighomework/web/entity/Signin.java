package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Signin {
    private int signin_id;
    private int stu_id;
    private String course_id;
    private int signin_ok;
    private int signin_state;
}
