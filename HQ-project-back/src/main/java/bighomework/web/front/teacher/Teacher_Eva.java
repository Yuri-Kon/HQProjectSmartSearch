package bighomework.web.front.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher_Eva {
    private String course_id;
    private String course_name;
    private int eva_score;
    private String eva_content;
}
