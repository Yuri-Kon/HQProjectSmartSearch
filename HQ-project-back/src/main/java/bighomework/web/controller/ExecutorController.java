package bighomework.web.controller;

import bighomework.web.entity.*;
import bighomework.web.front.executor.Teacher_Change;
import bighomework.web.front.student.Course_All;
import bighomework.web.mapper.*;
import bighomework.web.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/executor")
public class ExecutorController {
    //引用到的Mapper层
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ExecutorMapper executorMapper;
    @Autowired
    private StudentMapper studentMapper;
    //引用到的Service层
    @GetMapping("/findallteacher")
    public List<Teacher> Teacher_FindAll(){
        return teacherMapper.allTeacher();
    }

    @PostMapping("/searchTeacher")
    public List<Teacher> Teacher_FindSome(String teacher_id,String teacher_name){
        if(teacher_id.equals("") && teacher_name.equals("")){
            return teacherMapper.allTeacher();
        } else if (teacher_id.equals("") && !teacher_name.equals("")) {
            //当只有id为空的时候，那就根据name去查找
            return teacherMapper.SelectByName(teacher_name);
        } else if (!teacher_id.equals("") && teacher_name.equals("")) {
            //当只有name为空的时候，那就根据id去查找
            return teacherMapper.SelectById(Integer.valueOf(teacher_id));
        }else {
            return teacherMapper.SelectByNI(Integer.valueOf(teacher_id),teacher_name);
        }
    }

    @PostMapping("/getTeacher")
    public List<Teacher> Teacher_FindById(String teacher_id){
        System.out.println("de");
        System.out.println(teacher_id);
        return teacherMapper.SelectById(Integer.valueOf(teacher_id));
    }

    @PostMapping("/getTeacherCourse")
    public List<Course> Course_FindByTeacher(String teacher_id){
        return courseMapper.SelectByteacher_id(Integer.valueOf(teacher_id));
    }

    @PostMapping("/teacherModify")
    public Result Teacher_Modify(Teacher_Change t_change){
        //查看前端界面，发现这里无论如何都有对应上的teacher_id，所以不需要判断id是否存在对应的讲师
        teacherMapper.changeTeacher(Integer.valueOf(t_change.getTeacher_id()),t_change.getTeacher_name(),t_change.getTeacher_position()
        ,t_change.getTeacher_email(),t_change.getTeacher_tele(),t_change.getTeacher_field());
        return Result.FroFront("讲师信息修改成功",200);
    }

    @DeleteMapping("/teacherDelete/{teacher_id}")
    public Result Teacher_Delete(@PathVariable String teacher_id){
        //首先我们先看看这个老师所负责的课程中有多少个不符合删除要求的
        System.out.println(teacher_id);
        List<String> list = courseMapper.allState(Integer.valueOf(teacher_id));
        for(String i:list){
            if(!i.equals("未开课")&&!i.equals("已结束")){
                return Result.FroFront("该讲师正在负责开课中的课程，请调整课程信息后再删除！",400);
            }
        }
        //如果是未开课的课程，可以直接删掉
        List<Course> courses = courseMapper.SelectByteacher_id(Integer.valueOf(teacher_id));
        for(Course j:courses){
            if(j.getCourse_state().equals("未开课")){
                courseMapper.DeleteUnSign(j.getTeacher_id());
            }
            if(j.getCourse_state().equals("已结束")){
                String course_id = j.getCourse_id();
                courseMapper.UpdateOvered(0,course_id);
            }
        }
        //最后把这个讲师先从user里删掉
        List<Teacher> teachers = teacherMapper.SelectById(Integer.valueOf(teacher_id));
        String username=null;
        for(Teacher i:teachers){
            username=i.getUsername();
        }
        userMapper.DeleteByUser(username);
        //之后再把它从讲师列表里删除
        teacherMapper.DeleteById(Integer.valueOf(teacher_id));
        return Result.FroFront("该讲师删除成功",200);
    }

    //执行人查看他所负责的课程简略信息
    public List<Course_All> ChangeIdToName(List<Course> list){
        List<Course_All> N = new ArrayList<>();
        for(Course i:list){
            int teacher_id = i.getTeacher_id();
            String teacher_name = teacherMapper.NameById(teacher_id);
            N.add(new Course_All(i.getCourse_id(),i.getCourse_name(),i.getCourse_fee(),i.getCourse_state(),teacher_name));
        }
        return N;
    }
    @PostMapping("/getAllCourse")
    public List<Course_All> SelectSomeCourses(String username){
        String exe_id = executorMapper.exe_id(username);
        return ChangeIdToName(courseMapper.SelectByExe_id(exe_id));
    }

    //执行人查看课程详细信息
    @PostMapping("/getCourse")
    Course_IdToName FindCourse(String course_id){
        System.out.println(course_id);
        Course course = courseMapper.CourseById(course_id);
        //之后我们需要把Course给转化成新的course
        String teacher_name = teacherMapper.NameById(course.getTeacher_id());
        return new Course_IdToName(course.getCourse_id(),course.getExe_id(),teacher_name,course.getCourse_name(),course.getCourse_info(),
                course.getCourse_fee(),course.getCourse_state(),course.getCourse_start(),course.getCourse_end(),course.getCourse_place());
    }

    //执行人增加课程
    @PostMapping("/courseAdd")
    public Result addCourse(String username,String course_id,String course_name,String course_start,String course_end,String course_fee,
                            String course_teacher,String course_info,String course_state,String course_place){
        //首先需要查看课程编号是否重复
        if(courseMapper.countById(course_id)!=0){
            return Result.FroFront("该编号已经存在，请选择其他编号",400);
        }
        //如果该编号没有重复，那么就可以加入新的课程
        String exe_id = executorMapper.exe_id(username);
        courseMapper.addCourse(course_id,exe_id,Integer.valueOf(course_teacher),course_name,course_info,Double.valueOf(course_fee),
                course_state,course_start,course_end,course_place);
        return Result.FroFront("课程添加成功",200);
    }

    //执行人删除课程，危险删除，当课程状态
    @DeleteMapping("/courseDelete/{course_id}")
    public Result deleteCourse(@PathVariable String course_id){
        //首先查看这门课的状态
        System.out.println(course_id);
        String state = courseMapper.StateByCourse_id(course_id);
        if(state.equals("未开课") || state.equals("已结束")){
            //处在可删除状态，那就删除
            courseMapper.deleteCourse(course_id);
            return Result.FroFront("删除成功",200);
        }
        return Result.FroFront("该门课现在不处于可删除状态",400);
    }

    @PostMapping("/courseModify")
    public Result modifyCourse(String course_id,String course_name,String course_start,String course_end,String course_fee,
                               String course_teacher,String course_info,String course_state,String course_place){
        System.out.println(course_teacher);

        //既然编号肯定不会出现重复，那我们就可以直接进行修改
        courseMapper.ModifyCourse(course_id, teacherMapper.IDByName(course_teacher), course_name,course_info,Double.valueOf(course_fee),
                course_state,course_start,course_end,course_place);
        return Result.FroFront("课程修改成功",200);
    }

    @PostMapping("/getStudent")
    public List<Student> Student_FindById(String student_id){
        System.out.println("de");
        System.out.println(student_id);
        return studentMapper.SelectById(Integer.valueOf(student_id));
    }

    @PostMapping("/studentModify")
    public Result Student_Modify(Student_Change s_change){
        studentMapper.modifyStudent(Integer.valueOf(s_change.getStu_id()),s_change.getStu_name(),s_change.getStu_company(),s_change.getStu_position()
                , s_change.getStu_level(),s_change.getStu_email(),s_change.getStu_tele(),s_change.getStu_state());
        return Result.FroFront("学员信息修改成功",200);
    }

    @DeleteMapping("/studentDelete/{student_id}")
    public Result Student_Delete(@PathVariable String student_id){
        //删除学员没那么多要求，无论在上课与否直接删就行（被淘汰力）
        //先把这个学员先从user里删掉
        List<Student> students = studentMapper.SelectById(Integer.valueOf(student_id));
        String username=null;
        for(Student i:students){
            username=i.getUsername();
        }
        userMapper.DeleteByUser(username);
        //之后再把它从学员列表里删除
        studentMapper.deleteStudent(Integer.valueOf(student_id));
        return Result.FroFront("该学员删除成功",200);
    }
    @PostMapping("/studentAdd")
    public Result addStudent( int stu_id, String username, String stu_name, String stu_company, String stu_position,
                              String stu_level, String stu_email, String stu_tele, int stu_state){
        //先检查学号是否重复
        if(studentMapper.SelectById(stu_id) == null ) {
            return Result.FroFront("该学号已经存在，请输入其他学号", 400);
        }
        studentMapper.addStudent(stu_id,username,stu_name,stu_company,stu_position,stu_level,stu_email,stu_tele,stu_state);
        return Result.FroFront("学生添加成功",200);
    }

    @GetMapping("/findAllStudent")
    public List<Student> allStudent(){
        return studentMapper.allStu();
    }
}
