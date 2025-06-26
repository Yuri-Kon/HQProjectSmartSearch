package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//这个类指的是成绩单，对应软件公司、讲师、学员三个actor使用
public class Grade {
    int grade_id;
    String course_id;
    int stu_id;
    int stu_score;
    String teacher_evaluate;//这是啥
}