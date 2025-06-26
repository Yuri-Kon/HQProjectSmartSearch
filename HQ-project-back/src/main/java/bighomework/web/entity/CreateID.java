package bighomework.web.entity;

import bighomework.web.front.student.Course_All;
import bighomework.web.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

public class CreateID {
    //在这个类里面新建一个方法，使得输入一个List，得到一个范围之内，那个List之外的一个随机编号
    public Integer randomID(List<Integer> allId){
        int MAX = 99999;
        int MIN = 10000;
        int judge = 0;
        while (judge==0){
            int num = (int)(Math.random()*(MAX-MIN))+MIN;
            if(allId.size()==0){
                judge = num;
                break;
            }
            //如果这个新的随机数不在List中，那我们就可以输出之
            if(!allId.contains(num)){
                judge = num;
                break;
            }
        }
        return judge;
    }

    //比较两个字符类型的日期，如果A<=B就返回true，否则就返回false
    public boolean CompareDate(String A,String B){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date a; Date b;
        try {
            a = sdf.parse(A);
            b = sdf.parse(B);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(b.after(a) || b.equals(a)){
            return true;
        }else {
            return false;
        }
    }

    //以下部分用于测试该函数是否正确
    /*public static void main(String[] args){
        CreateID createID = new CreateID();
        System.out.println(createID.CompareDate("2005-1-20","2005-1-2"));
    }*/
}
