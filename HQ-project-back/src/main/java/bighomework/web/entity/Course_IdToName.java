package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course_IdToName {
    private String course_id;
    private String exe_id;
    private String course_teacher;
    private String course_name;
    private String course_info;
    private double course_fee;
    private String course_state;
    private String course_start;
    private String course_end;
    private String course_place;
}
