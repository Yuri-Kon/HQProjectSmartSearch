package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apply {
    private int apply_id;
    private String username;
    private String company_name;
    private Double apply_budget;
    private String apply_want;
    private int stu_num;
    private String company_tele;
}
