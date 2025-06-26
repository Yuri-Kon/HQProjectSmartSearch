package bighomework.web.controller;

import bighomework.web.entity.*;
import bighomework.web.mapper.*;
import jdk.javadoc.doclet.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ExecutorMapper executorMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @PostMapping("/getAllBill")
    public List<Bill> getAllBill(String username){
        return billMapper.allBills();
    }

    @PostMapping("/searchBill")
    public List<Bill> searchBill(String bill_start,String bill_end,String bill_id){
        CreateID createID = new CreateID();
        List<Bill> allBills = billMapper.allBills();
        List<Bill> searchBills = new ArrayList<>();
        //首先查看id为不为空
        if(bill_id.length()==0){
            //那就看时间是不是空的，如果时间不为空，那我就查这段时间内的课程
            if(bill_start.length()!=0 && bill_end.length()!=0){
                for(Bill i:allBills){
                    if(createID.CompareDate(bill_start,i.getBill_time())&& createID.CompareDate(i.getBill_time(),bill_end)){
                        searchBills.add(i);
                    }
                }
                return searchBills;
            }else {
                return allBills;
            }
        }else {
            return billMapper.BillsByIDs(Integer.valueOf(bill_id));
        }
    }

    @PostMapping("/getAllReport")
    public List<Report> allReports(String username){
        //首先看看现在的日期
        String time =  LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //首先查看某个日报表是否已经存在，如果不存在，那就增加
        if(reportMapper.CountByTime(time)==0){
            CreateID createID = new CreateID();
            int report_id = createID.randomID(reportMapper.allIDs());
            double incomes = 0;
            List<Double> moneys = billMapper.Moneys(time);
            for(Double i:moneys){
                incomes += i;
            }
            reportMapper.addReport(report_id,time,time,incomes, teacherMapper.CountTeachers(), courseMapper.course_num(),
                    studentMapper.stu_num(), companyMapper.company_num(), executorMapper.executor_num());
            //刷新完成，那就可以查询
            return reportMapper.allReports();
        }
        else {
            //如果日报表已经存在，那就刷新
            int report_id = reportMapper.IdByTime(time);
            double incomes = 0;
            List<Double> moneys = billMapper.Moneys(time);
            for(Double i:moneys){
                incomes += i;
            }
            reportMapper.changeReport(report_id,incomes, teacherMapper.CountTeachers(), courseMapper.course_num(),
                    studentMapper.stu_num(), companyMapper.company_num(), executorMapper.executor_num());
            return reportMapper.allReports();
        }
    }

    @PostMapping("/getReport")
    public List<Report> getReports(String report_id){
        if(report_id.length()==0){
            return reportMapper.allReports();
        }else {
            return reportMapper.chooseTheReport(Integer.valueOf(report_id));
        }
    }

    @PostMapping("/searchReport")
    public List<Report> searchReport(String report_start,String report_end,String report_id){
        CreateID createID = new CreateID();
        List<Report> allReports = reportMapper.allReports();
        List<Report> searchReports = new ArrayList<>();
        if(report_id.length()==0){
            if(report_start.length()!=0&&report_end.length()!=0){
                for(Report i:allReports){
                    if(createID.CompareDate(report_start,i.getReport_start()) && createID.CompareDate(i.getReport_end(),report_end)){
                        searchReports.add(i);
                    }
                }
                return searchReports;
            }else {
                return allReports;
            }
        }else {
            return reportMapper.chooseTheReport(Integer.valueOf(report_id));
        }
    }

    @PostMapping("/getAllApp")
    public List<Apply> getAllApp(String username){
        return applicationMapper.allApps();
    }

    @PostMapping("/getApp")
    public Apply getApp(int apply_id){
        return applicationMapper.getApp(apply_id);
    }

    @PostMapping("/findAllExecutor")
    public List<Executor> findAllExecutor(){
        return executorMapper.FindAll();
    }

    @PostMapping("/getCourses")
    public List<Course> getCourses(String exe_id){
        return courseMapper.SelectByExe_id(exe_id);
    }
}
