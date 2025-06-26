package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class getGrade {
    String stu_name;
    String course_id;
    String course_name;
    int stu_id;
    int stu_score;
    String teacher_evaluate;//这是啥
}

