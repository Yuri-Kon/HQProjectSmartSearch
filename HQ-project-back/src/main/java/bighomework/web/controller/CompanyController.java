package bighomework.web.controller;

import bighomework.web.entity.*;
import bighomework.web.front.company.Choose_To_Pay;
import bighomework.web.front.company.Payment_Detail;
import bighomework.web.mapper.*;
import bighomework.web.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private SignupMapper signupMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @PostMapping("/getAllPayment")
    public List<Signup_IdToName> getAllPayment(String username){
        //首先根据公司的username查找公司
        String company_name = companyMapper.NameByUsername(username);
        //接着根据公司的名称查找对应的员工
        List<Integer> students = studentMapper.StudentsByCompany(company_name);
        //然后我们需要根据学生信息来查找他们选的课程
        Set<String> course_ids = new HashSet<>();
        for(Integer i:students){
            List<String> IDs = signupMapper.CoursesByStu_id(i);
            course_ids.addAll(IDs);
        }
        //这是需要最终返回的缴费信息
        List<Signup_IdToName> payments = new ArrayList<>();
        for(String course_id:course_ids){
            String course_name = courseMapper.CourseById(course_id).getCourse_name();
            int signup_state = 1;
            //查看这门课是否已经全部缴费
            for(Integer stu_id:students){
                if(signupMapper.state(stu_id,course_id)==0){
                    signup_state = 0;
                }
            }
            payments.add(new Signup_IdToName(course_id,course_name,signup_state));
        }
        return payments;
    }

    @PostMapping("/getPaymentStudent")
    public List<Payment_Detail> getPayStu(String username,String course_id){
        List<Payment_Detail> paymentDetails = new ArrayList<>();
        //首先根据公司的username查找公司
        String company_name = companyMapper.NameByUsername(username);
        //接着根据公司的名称查找对应的员工
        List<Integer> students = studentMapper.StudentsByCompany(company_name);
        for(Integer i:students){
            String stu_name = studentMapper.NameById(i);
            int signup_state = signupMapper.state(i,course_id);
            paymentDetails.add(new Payment_Detail(i,stu_name,signup_state));
        }
        return paymentDetails;
    }

    @PostMapping("/pay")
    public Result pay(@RequestBody List<Choose_To_Pay> studentList){
        String username = null;
        String course_id = null;
        for(Choose_To_Pay i:studentList){
            username = i.getUsername();
            course_id = i.getCourse_id();
        }
        CreateID createID = new CreateID();
        //首先我需要获得所有的学生id
        for(Choose_To_Pay i:studentList){
            int stu_id = Integer.valueOf(i.getStu_id());
            signupMapper.pay(stu_id,course_id,1);
            double course_fee = courseMapper.CourseById(course_id).getCourse_fee();
            String time =  LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            billMapper.addBill(createID.randomID(billMapper.allIDs()),time,course_id,username,course_fee);
        }
        return Result.FroFront("缴费成功",200);
    }

    @PostMapping("CompanyGetGrade")
    public List<Grade> GetGrade(String course_id){
        return gradeMapper.SelectByCourse_id(course_id);
    }

    @PostMapping("/getGrade")
    public List<getGrade> getGrades(String username,String course_id){
        List<getGrade> list = new ArrayList<>();
        String company_name = companyMapper.NameByUsername(username);
        List<Integer> stu_ids = studentMapper.StudentsByCompany(company_name);
        for(Integer i:stu_ids){
            Grade grade = gradeMapper.SelectByCS_id(course_id,i);
            String stu_name = studentMapper.NameById(grade.getStu_id());
            String course_name = courseMapper.CourseById(grade.getCourse_id()).getCourse_name();
            list.add(new getGrade(stu_name,grade.getCourse_id(),course_name,i, grade.getStu_score(), grade.getTeacher_evaluate()));
        }
        return list;
    }

    @PostMapping("/addApp")
    public Result addApp(String username,String apply_budget,String apply_want,int stu_num,String company_tele){
        //首先查看是否已经申请过
        if(applicationMapper.selectByUsername(username)!=0){
            return Result.FroFront("您已经提交过申请，请等待公司经理与您联系",400);
        }else {
            CreateID createID = new CreateID();
            int apply_id = createID.randomID(applicationMapper.allIDs());
            String company_name = companyMapper.NameByUsername(username);
            applicationMapper.addApp(apply_id,username,company_name,Double.valueOf(apply_budget),apply_want,Integer.valueOf(stu_num),company_tele);
            return Result.FroFront("申请成功，请等待公司经理与您联系",200);
        }
    }

}
