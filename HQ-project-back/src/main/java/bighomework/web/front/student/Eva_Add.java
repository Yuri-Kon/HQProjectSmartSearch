package bighomework.web.front.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Eva_Add {
    private String course_id;
    private String stu_id;
    private String teacher_id;
    private String eva_score;
    private String eva_content;
}
