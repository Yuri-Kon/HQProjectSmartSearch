package bighomework.web.service;

import bighomework.web.entity.Course;
import bighomework.web.front.student.Eva_Add;
import bighomework.web.util.Result;

import java.util.List;

public interface StudentService {
    Result BindIn(String username, String stu_name, String stu_company, String stu_position,
                  String stu_tele, String stu_email, String stu_level, String company_key);

    List<Course> CoursesByStu(String username);
}
