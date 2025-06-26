package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int stu_id;
    private String username;
    private String stu_name;
    private String stu_company;
    private String stu_position;
    private String stu_level;
    private String stu_email;
    private String stu_tele;
    private int stu_state;
}
