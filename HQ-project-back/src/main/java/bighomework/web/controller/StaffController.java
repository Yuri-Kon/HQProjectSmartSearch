package bighomework.web.controller;

import bighomework.web.entity.CreateID;
import bighomework.web.mapper.SigninMapper;
import bighomework.web.mapper.SignupMapper;
import bighomework.web.mapper.StudentMapper;
import bighomework.web.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private SignupMapper signupMapper;
    @Autowired
    private SigninMapper signinMapper;


    //工作人员发起签到
    @PostMapping("/startCheck")
    public Result startCheck(String course_id){
        if(signinMapper.countSignins(course_id)!=0){
            return Result.FroFront("不可重复发起签到",400);
        }
        //首先我需要找到所有选这门课的人
        List<Integer> stu_ids = signupMapper.stu_ids(course_id);
        CreateID createID = new CreateID();
        for(Integer i:stu_ids){
            int signin_id = createID.randomID(signinMapper.allIDs());
            signinMapper.startSignin(signin_id,i,course_id,1,0);
        }
        return Result.FroFront("发起签到成功",200);
    }

    @PostMapping("/endCheck")
    public Result endCheck(String course_id){
        System.out.println(course_id);
        signinMapper.endSignin(0,course_id);
        return Result.FroFront("已停止签到",200);
    }



}
