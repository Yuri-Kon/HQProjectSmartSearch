package bighomework.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.pattern.PathPattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluate_Change {
    private String Course_id;
    private String Course_name;
    private int eva_score;
    private String eva_content;
}
