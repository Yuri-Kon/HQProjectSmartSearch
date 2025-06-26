package bighomework.web.service.impl;

import bighomework.web.entity.Course;
import bighomework.web.entity.CreateID;
import bighomework.web.mapper.CompanyMapper;
import bighomework.web.mapper.CourseMapper;
import bighomework.web.mapper.SignupMapper;
import bighomework.web.mapper.StudentMapper;
import bighomework.web.service.StudentService;
import bighomework.web.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private SignupMapper signupMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Result BindIn(String username, String stu_name, String stu_company, String stu_position, String stu_tele, String stu_email, String stu_level, String company_key) {
        //首先我们需要判断这个公司的秘钥是否正确
        if(!company_key.equals(companyMapper.SelectByName(stu_company))){
            return Result.FroFront("公司秘钥错误",400);
        }
        //接着我们需要判断是否已经进行过绑定
        //如果绑定过
        if(studentMapper.SelectByUsername(username).size()!=0){
            //那我们还需要查看是否是新老学员，这需要我们去选课单里面查看这个学生所有选的课程，然后看看这些课程里面有多少是已经结束的
            int stu_id = studentMapper.IdByUsername(username);
            int stu_state = 0;
            for(String i:signupMapper.CoursesByStu_id(stu_id)){
                if(courseMapper.StateByCourse_id(i).equals("已结束")){
                    stu_state=1;
                }
            }
            studentMapper.modifyStudent(stu_id,stu_name,stu_company,stu_position,stu_level,stu_email,stu_tele,stu_state);
            return Result.FroFront("您的绑定信息已经修改成功",200);
        }
        //如果是未绑定过的，那就一定是新的学员
        CreateID createID = new CreateID();
        int stu_id = createID.randomID(studentMapper.allStu_id());
        int stu_state = 0;
        studentMapper.addStudent(stu_id,username,stu_name,stu_company,stu_position,stu_level,stu_email,stu_tele,stu_state);
        return Result.FroFront("绑定成功",200);
    }

    @Override
    public List<Course> CoursesByStu(String username) {
        int stu_id = studentMapper.IdByUsername(username);
        List<String> Course_ids = signupMapper.CoursesByStu_id(stu_id);
        List<Course> courses = new ArrayList<>();
        for(String i:Course_ids){
            courses.add(courseMapper.CourseById(i));
        }
        return courses;
    }
}
