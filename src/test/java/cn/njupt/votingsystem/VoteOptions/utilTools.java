package cn.njupt.votingsystem.VoteOptions;

import cn.hutool.json.JSONUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/26
 **/
public class utilTools {

    public static void main(String[] args) {

        HashMap<String, Integer> mp = new HashMap<>();
        mp.put("fafaf", 2);
        mp.put("fafadffaf", 2);
        ArrayList<String> arr= new ArrayList<>();
        arr.add("fafa");
        arr.add("fafa");
        System.out.println(JSONUtil.toJsonStr(arr));
        System.out.println(JSONUtil.toJsonStr(mp));
        LocalDateTime now = LocalDateTime.now();

        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format);
    }
}
