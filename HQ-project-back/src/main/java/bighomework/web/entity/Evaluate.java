package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluate {
    private int eva_id;
    private String course_id;
    private int stu_id;
    private int teacher_id;
    private int eva_score;
    private String eva_content;
}
