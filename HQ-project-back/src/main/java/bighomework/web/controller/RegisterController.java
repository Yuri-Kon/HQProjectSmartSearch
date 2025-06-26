package bighomework.web.controller;

import bighomework.web.entity.User;
import bighomework.web.front.register.Register_Company;
import bighomework.web.front.register.Register_Executor;
import bighomework.web.front.register.Register_Staff;
import bighomework.web.front.register.Register_Teacher;
import bighomework.web.mapper.CompanyMapper;
import bighomework.web.mapper.ExecutorMapper;
import bighomework.web.mapper.UserMapper;
import bighomework.web.service.RegisterService;
import bighomework.web.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/register")
public class RegisterController {

    //引用到的Mapper层
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ExecutorMapper executorMapper;

    //引用到的Service层
    @Autowired
    private RegisterService registerService;

    @PostMapping("/teacher")
    public Result register_teacher(Register_Teacher r_teacher){
        String username = r_teacher.getUsername();
        if(userMapper.CountByUser(username)!=0){
            return Result.FroFront("用户名已存在",400);
        }
        registerService.addTeacher(r_teacher);
        return Result.FroFront("注册成功",200);
    }

    @PostMapping("/company")
    public Result register_company(Register_Company r_company){
        String username = r_company.getUsername();
        if(userMapper.CountByUser(username)!=0){
            return Result.FroFront("用户名已存在",400);
        }
        if(companyMapper.CountByName(r_company.getCompany_name())!=0){
            return Result.FroFront("公司名已存在",401);
        }
        registerService.addCompany(r_company);
        return Result.FroFront("注册成功",200);
    }

    @PostMapping("/student")
    public Result register_student(User student){
        if(userMapper.CountByUser(student.getUsername())!=0){
            return Result.FroFront("用户名已存在",400);
        }
        userMapper.addUser(student.getUsername(),student.getPassword(),student.getUsertype());
        return Result.FroFront("注册成功",200);
    }

    @PostMapping("/executor")
    public Result register_executor(Register_Executor r_executor){
        System.out.println(r_executor);
        if(userMapper.CountByUser(r_executor.getUsername())!=0){
            return Result.FroFront("用户名已存在",400);
        }
        String key = companyMapper.SelectByName("HQcompany");
        if(!r_executor.getCompany_key().equals(key)){
            return Result.FroFront("密钥发生错误",401);
        }
        //当编号出现重复时
        if(executorMapper.CountByExe_id(r_executor.getExe_id())!=0){
            return Result.FroFront("执行人编号重复",402);
        }
        registerService.addExecutor(r_executor);
        return Result.FroFront("注册成功",200);
    }

    @PostMapping("/staff")
    public Result register_staff(Register_Staff staff){
        if(userMapper.CountByUser(staff.getUsername())!=0){
            return Result.FroFront("用户名已存在",400);
        }
        String key = companyMapper.SelectByName("HQcompany");
        if(!staff.getCompany_key().equals(key)){
            return Result.FroFront("密钥错误",401);
        }
        userMapper.addUser(staff.getUsername(), staff.getPassword(), staff.getUsertype());
        return Result.FroFront("注册成功",200);
    }

    @PostMapping("/manager")
    public Result register_manager(Register_Staff manager){
        if(userMapper.CountByUser(manager.getUsername())!=0){
            return Result.FroFront("用户名已存在",400);
        }
        String key = companyMapper.SelectByName("HQcompany");
        if(!manager.getCompany_key().equals(key)){
            return Result.FroFront("密钥错误",401);
        }
        userMapper.addUser(manager.getUsername(), manager.getPassword(), manager.getUsertype());
        return Result.FroFront("注册成功",200);
    }
}
