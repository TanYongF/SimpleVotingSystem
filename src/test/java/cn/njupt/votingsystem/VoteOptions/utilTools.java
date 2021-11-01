package cn.njupt.votingsystem.VoteOptions;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/26
 **/
public class utilTools {

    public static void main(String[] args) {
//        System.out.println(LocalDateTime.now(
        Date date = new Date();
        System.out.println(DateUtil.date());
        System.out.println(date);
        Date curDate = new Date();
        for(int i = 0; i < 30; i++){
            curDate = DateUtil.offset(curDate, DateField.DAY_OF_MONTH, 1);
            System.out.println(curDate);
        }

    }

}
