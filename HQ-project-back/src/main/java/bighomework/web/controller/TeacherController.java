package bighomework.web.controller;

import bighomework.web.entity.*;
import bighomework.web.front.student.Course_All;
import bighomework.web.front.teacher.Teacher_Eva;
import bighomework.web.mapper.*;
import bighomework.web.util.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private EvaluateMapper evaluateMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private SignupMapper signupMapper;

    @PostMapping("/getCourseEvaluate")
    public List<Evaluate_Change> Teacher_Eva(String username){
        //首先根据username来寻找讲师id
        int teacher_id = teacherMapper.IdByUsername(username);
        List<Evaluate> list = evaluateMapper.SelectByT_id(teacher_id);
        List<Evaluate_Change> evas = new ArrayList<>();
        for(Evaluate i:list){
            String course_name = courseMapper.CourseById(i.getCourse_id()).getCourse_name();
            evas.add(new Evaluate_Change(i.getCourse_id(),course_name,i.getEva_score(),i.getEva_content()));
        }
       return evas;
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

    @PostMapping("/searchSelected")
    public List<Course_All> SelectedCourse(String username){
        //首先是根据username查找到讲师的id
        int teacher_id = teacherMapper.IdByUsername(username);
        return ChangeIdToName(courseMapper.SelectByteacher_id(teacher_id));
    }

    //接口1，传给前端getgrade类型，循环遍历对其赋值
    @PostMapping("/getCourseStudent")
    public List<getGrade> GetCourse(String course_name,String course_id){
        List<getGrade> getgrade = new ArrayList<>();
        List<Integer> stu_id = signupMapper.stu_ids(course_id);
        for(Integer sid:stu_id){
            String stu_name = studentMapper.NameById(sid);
            //String course_name = courseMapper.NameByCourseId(g.getCourse_id());
            int stu_score = gradeMapper.SelectScore(sid,course_id);
            String teacher_evaluate = gradeMapper.SelectEva(sid,course_id);
            getgrade.add(new getGrade(stu_name,course_id,course_name,sid,stu_score,teacher_evaluate));
        }
        return getgrade;
    }

    //接口2，通过stu_id, course_id查询成绩
    @PostMapping("/getStudentGrade")
    public Grade GetGrade(int stu_id,String course_id){
        return gradeMapper.SelectByCS_id(course_id,stu_id);
    }

    //接口3
    @PostMapping("/enterGrade")
    public Result enterGrade(int stu_id, String course_id, int stu_score, String teacher_evaluate){
        gradeMapper.modifyGrade(stu_id,course_id,stu_score,teacher_evaluate);
        return Result.FroFront("签到成功",200);
    }

}

