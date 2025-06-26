package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Signin_Change {
    private String course_name;
    private int signin_state;
    private int signin_ok;
    private String course_id;
}
