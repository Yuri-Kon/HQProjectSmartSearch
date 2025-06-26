package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    private int bill_id;
    private String bill_time;
    private String course_id;
    private String username;
    private String bill_income;
}
