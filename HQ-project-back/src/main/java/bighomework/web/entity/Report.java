package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private int report_id;
    private String report_start;
    private String report_end;
    private double report_income;
    private int teacher_num;
    private int course_num;
    private int stu_num;
    private int company_num;
    private int executor_num;
}
