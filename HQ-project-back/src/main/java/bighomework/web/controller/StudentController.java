package bighomework.web.controller;

import bighomework.web.entity.*;
import bighomework.web.front.student.Course_All;
import bighomework.web.front.student.Eva_Add;
import bighomework.web.mapper.*;
import bighomework.web.service.StudentService;
import bighomework.web.service.SmartCourseSearchService;
import bighomework.web.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {
  // 引用的Mapper
  @Autowired
  private EvaluateMapper evaluateMapper;
  @Autowired
  private CourseMapper courseMapper;
  @Autowired
  private StudentMapper studentMapper;
  @Autowired
  private TeacherMapper teacherMapper;
  @Autowired
  private SignupMapper signupMapper;
  @Autowired
  private BillMapper billMapper;
  @Autowired
  private GradeMapper gradeMapper;
  @Autowired
  private SigninMapper signinMapper;
  @Autowired
  private SmartCourseSearchService smartCourseSearchService;

  // 引用的Service
  @Autowired
  private StudentService studentService;

  @PostMapping("/courseEvaluate")
  public Result addEvaluate(String course_id, String username, String eva_score, String eva_content) {
    // 首先查看是否已经评价过，如果评价过那就修改，不然就增加
    System.out.println(course_id);
    // System.out.println(username);
    // System.out.println(eva_score);
    // System.out.println(eva_content);
    int stu_id = studentMapper.IdByUsername(username);
    if (evaluateMapper.CountTheEva(stu_id, course_id) != 0) {
      evaluateMapper.ModifyTheEva(stu_id, course_id, Integer.valueOf(eva_score), eva_content);
      return Result.FroFront("评价修改成功", 200);
    }
    System.out.println("aaa");
    CreateID createID = new CreateID();
    int eva_id = createID.randomID(evaluateMapper.allIDs());
    int teacher_id = courseMapper.CourseById(course_id).getTeacher_id();
    evaluateMapper.addEvaluate(eva_id, course_id, stu_id, teacher_id, Integer.valueOf(eva_score), eva_content);
    return Result.FroFront("课程评价添加成功", 200);
  }

  @PostMapping("/getStudentInfo")
  public List<Student> SelectByUsername(String username) {
    return studentMapper.SelectByUsername(username);
  }

  @PostMapping("/bindid")
  public Result BindIn(String username, String stu_name, String stu_company, String stu_position,
      String stu_tele, String stu_email, String stu_level, String company_key) {
    return studentService.BindIn(username, stu_name, stu_company, stu_position, stu_tele, stu_email, stu_level,
        company_key);
  }

  @PostMapping("/getStudentCourse")
  public List<Course_All> StuCourse(String username) {
    return ChangeIdToName(studentService.CoursesByStu(username));
  }

  @PostMapping("/searchSelected")
  public List<Course_All> StuCourses(String username) {
    return ChangeIdToName(studentService.CoursesByStu(username));
  }

  public List<Course_All> ChangeIdToName(List<Course> list) {
    List<Course_All> N = new ArrayList<>();
    for (Course i : list) {
      int teacher_id = i.getTeacher_id();
      String teacher_name = teacherMapper.NameById(teacher_id);
      N.add(new Course_All(i.getCourse_id(), i.getCourse_name(), i.getCourse_fee(), i.getCourse_state(), teacher_name));
    }
    return N;
  }

  @PostMapping("/searchCourseEvaluate")
  public List<Course_All> StuSomeCourse(String username, String course_id, String course_name) {
    List<Course> list = studentService.CoursesByStu(username);
    List<Course> NewList = new ArrayList<>();
    // 判断course_id和course_name哪个是空的
    if (course_id.length() == 0 && course_name.length() == 0) {
      return ChangeIdToName(list);
    } else if (course_id.length() != 0 && course_name.length() == 0) {
      for (Course i : list) {
        if (i.getCourse_id().equals(course_id)) {
          NewList.add(i);
        }
      }
      return ChangeIdToName(NewList);
    } else if (course_id.length() == 0 && course_name.length() != 0) {
      System.out.println("asd");
      for (Course i : list) {
        if (i.getCourse_name().equals(course_name)) {
          NewList.add(i);
        }
      }
      return ChangeIdToName(NewList);
    } else {
      for (Course i : list) {
        if (i.getCourse_name().equals(course_name) && i.getCourse_id().equals(course_id)) {
          NewList.add(i);
        }
      }
      return ChangeIdToName(NewList);
    }
  }

  @PostMapping("/getCourseEvaluate")
  public Evaluate StuFindEva(String username, String course_id) {
    int stu_id = studentMapper.IdByUsername(username);
    // 根据stu_id和course_id前往评价表去查找对应的课程
    return evaluateMapper.SearchBySCID(stu_id, course_id);
  }

  // 学生报名新的课程
  @PostMapping("/addSignup")
  public Result addSignup(String username, String course_id) {
    int stu_id = studentMapper.IdByUsername(username);
    if (signupMapper.CountByTwoIDs(stu_id, course_id) != 0) {
      return Result.FroFront("您已经选择过此课程", 400);
    }
    // 如果没选过那就让他选课
    CreateID createID = new CreateID();
    int signup_id = createID.randomID(signupMapper.allIDS());
    signupMapper.addSignup(signup_id, stu_id, course_id, 0);
    return Result.FroFront("选课成功", 200);
  }

  // 学生退课
  @PostMapping("/dropCourse")
  public Result deleteSignup(String username, String course_id) {
    System.out.println(username);
    System.out.println(course_id);
    int stu_id = studentMapper.IdByUsername(username);
    if (signupMapper.CountByTwoIDs(stu_id, course_id) == 0) {
      return Result.FroFront("您还未选择此课程，无法退课", 400);
    }
    // 如果选过了那就让他退课
    signupMapper.dropSignup(stu_id, course_id);
    return Result.FroFront("退课成功", 200);
  }

  // 学生查询自己所有课程的缴费状态
  public List<Signup_IdToName> changeSignup(List<Signup> list) {
    List<Signup_IdToName> signupIdToNames = new ArrayList<>();
    for (Signup i : list) {
      String course_name = courseMapper.CourseById(i.getCourse_id()).getCourse_name();
      signupIdToNames.add(new Signup_IdToName(i.getCourse_id(), course_name, i.getSignup_state()));
    }
    return signupIdToNames;
  }

  @PostMapping("/getAllPayment")
  public List<Signup_IdToName> AllPayment(String username) {
    // 首先根据username查询学生id
    int stu_id = studentMapper.IdByUsername(username);
    return changeSignup(signupMapper.allSignup(stu_id));
  }

  @PostMapping("/pay")
  public Result pay(String username, String course_id) {
    CreateID createID = new CreateID();
    int stu_id = studentMapper.IdByUsername(username);
    // 首先需要判断是否已经缴费
    int state = signupMapper.state(stu_id, course_id);
    if (state == 1) {
      return Result.FroFront("您已缴纳过此课程的培训费用", 400);
    } else {
      signupMapper.pay(stu_id, course_id, 1);
      double course_fee = courseMapper.CourseById(course_id).getCourse_fee();
      String time = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      billMapper.addBill(createID.randomID(billMapper.allIDs()), time, course_id, username, course_fee);
      return Result.FroFront("您已成功缴纳费用", 200);
    }
  }

  @PostMapping("/StudentGetGrade")
  public Grade StuGetGrade(int stu_id, String course_id) {
    return gradeMapper.SelectByCS_id(course_id, stu_id);
  }

  @PostMapping("/getAllCheck")
  public List<Signin_Change> getAllCheck(String username) {
    List<Signin_Change> list = new ArrayList<>();
    System.out.println(username);
    // 根据学生的username来查找学生的id
    int stu_id = studentMapper.IdByUsername(username);
    List<Signin> allCheck = signinMapper.allSigns(stu_id);
    System.out.println(allCheck);
    for (Signin i : allCheck) {
      String course_name = courseMapper.CourseById(i.getCourse_id()).getCourse_name();
      list.add(new Signin_Change(course_name, i.getSignin_state(), i.getSignin_ok(), i.getCourse_id()));
    }
    System.out.println(list);
    return list;
  }

  @PostMapping("/addCheck")
  public Result addCheck(String username, String course_id) {
    System.out.println(username);
    System.out.println(course_id);
    int stu_id = studentMapper.IdByUsername(username);
    signinMapper.Signin(stu_id, course_id, 1);
    return Result.FroFront("签到成功", 200);
  }

  @PostMapping("/getAllGrade")
  public List<getGrade> getAllGrade(String username) {
    int stu_id = studentMapper.IdByUsername(username);
    System.out.println(stu_id);
    List<Grade> grades = gradeMapper.SelectByStu_id(stu_id);
    System.out.println(grades);
    List<getGrade> getGrades = new ArrayList<>();
    for (Grade i : grades) {
      String course_name = courseMapper.CourseById(i.getCourse_id()).getCourse_name();
      getGrades.add(
          new getGrade("a", i.getCourse_id(), course_name, i.getGrade_id(), i.getStu_score(), i.getTeacher_evaluate()));
    }
    return getGrades;
  }

  @PostMapping("/searchSmartCourse")
  public List<CourseWithTeacherName> searchSmartCourse(@RequestBody Map<String, String> input) {
    String query = input.get("query");
    System.out.println("💡 [StudentController] 接收到的 query: [" + query + "]"); // 调试代码
    if (query == null || query.isBlank()) {
      return Collections.emptyList(); // 或抛出参数异常
    }
    try {
      return smartCourseSearchService.searchCoursesByQuery(query);
    } catch (Exception e) {
      throw new RuntimeException("运行失败" + e.getMessage(), e);
    }
  }

}
