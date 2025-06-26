package bighomework.web.front.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course_All {
    private String course_id;
    private String course_name;
    private double course_fee;
    private String course_state;
    private String course_teacher;
}
