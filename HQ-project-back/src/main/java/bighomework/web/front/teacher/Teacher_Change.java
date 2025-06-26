package bighomework.web.front.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher_Change {
    private int teacher_id;
    private String teacher_name;
    private String teacher_position;
    private String teacher_email;
    private String teacher_tele;
    private String teacher_field;
}
