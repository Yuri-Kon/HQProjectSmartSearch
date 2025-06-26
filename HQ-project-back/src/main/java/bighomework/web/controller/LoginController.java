package bighomework.web.controller;

import bighomework.web.entity.Course;
import bighomework.web.entity.User;
import bighomework.web.front.student.Course_All;
import bighomework.web.mapper.CourseMapper;
import bighomework.web.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import bighomework.web.service.LoginService;
import bighomework.web.util.Result;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    //登录功能
    @PostMapping("/login")
    public Result login(User user) {
        if(loginService.Login(user.getUsername(), user.getPassword(),user.getUsertype())>0){
            return Result.FroFront("登陆成功",200);
        }
        else {
            System.out.println(user);
            return Result.FroFront("登录失败", 400);
        }
    }

    public List<Course_All> ChangeIdToName(List<Course> list){
        List<Course_All> N = new ArrayList<>();
        for(Course i:list){
            int teacher_id = i.getTeacher_id();
            String teacher_name = teacherMapper.NameById(teacher_id);
            N.add(new Course_All(i.getCourse_id(),i.getCourse_name(),i.getCourse_fee(),i.getCourse_state(),teacher_name));
        }
        return N;
    }

    @GetMapping("/getAllCourse")
    public List<Course_All> allCourse(){
        return ChangeIdToName(courseMapper.allCourses());
    }
}
