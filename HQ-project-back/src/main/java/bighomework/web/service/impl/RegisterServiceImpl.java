package bighomework.web.service.impl;

import bighomework.web.entity.CreateID;
import bighomework.web.front.register.Register_Company;
import bighomework.web.front.register.Register_Executor;
import bighomework.web.front.register.Register_Teacher;
import bighomework.web.mapper.CompanyMapper;
import bighomework.web.mapper.ExecutorMapper;
import bighomework.web.mapper.TeacherMapper;
import bighomework.web.mapper.UserMapper;
import bighomework.web.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ExecutorMapper executorMapper;
    @Override
    public void addTeacher(Register_Teacher r_teacher) {
        List<Integer> allId = teacherMapper.allId();
        CreateID createID = new CreateID();
        int teacher_id = createID.randomID(allId);
        //我们先在用户表里面进行添加
        userMapper.addUser(r_teacher.getUsername(), r_teacher.getPassword(), r_teacher.getUsertype());
        //在teacher表里进行添加
        teacherMapper.addTeacher(teacher_id,r_teacher.getTeacher_name(), r_teacher.getTeacher_position(), r_teacher.getTeacher_email(),
                r_teacher.getTeacher_tele(), r_teacher.getTeacher_field(), r_teacher.getUsername());
    }

    @Override
    public void addCompany(Register_Company r_company) {
        //现在用户表中进行添加
        userMapper.addUser(r_company.getUsername(),r_company.getPassword(),r_company.getUsertype());
        //再在公司表中进行添加
        companyMapper.addCompany(r_company.getCompany_name(), r_company.getCompany_key(), r_company.getUsername(),r_company.getCompany_tele());
    }

    @Override
    public void addExecutor(Register_Executor r_executor) {
        //在用户中添加
        userMapper.addUser(r_executor.getUsername(), r_executor.getPassword(), r_executor.getUsertype());
        //在执行人表中进行添加
        executorMapper.addExecutor(r_executor.getExe_id(),r_executor.getExe_name(),r_executor.getUsername());
    }

}
