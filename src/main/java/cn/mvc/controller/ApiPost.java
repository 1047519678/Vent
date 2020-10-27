package cn.mvc.controller;

import cn.mvc.service.Service_interface_Login;
import cn.mvc.tools.DateUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//渲染
@Controller
//指向的目录包
@RequestMapping("")

public class ApiPost {
    @Autowired
    private Service_interface_Login checkuser;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(value = "/ApiKanBanModel", produces = "application/json")
    @ResponseBody
    public JSONObject ApiKanBanModel(Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("model", "model");
        List<Map<String, Object>> kanbanmodel = checkuser.server_getmodel(map);
        JSONObject json = new JSONObject();
        json.put("model", kanbanmodel);
        json.put("code", "apikanbanmodel");
        return json;
    }

    @RequestMapping(value = "/ApiKanBantable", produces = "application/json")
    @ResponseBody
    public JSONObject ApiKanBantable(@RequestBody Map<String, Object> mapModel, Map<String, Object> map) throws UnsupportedEncodingException, ParseException {

        JSONObject json = new JSONObject();
        Date d = new Date();
        List<Map<String, Object>> ylistdata = new ArrayList<>();

        //设置前三月 前三周数据
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String weekDate1 = DateUtils.getStartEndOfWeek(new Date());
        String[] array = weekDate1.split(",");
        String start_time = array[0];
        String end_time = array[1];
        Date dBegin = df.parse(start_time);
        Date dEnd = df.parse(end_time);
        //获取x轴日期数据
        Map<String, Object> xdate = DateUtils.findDates(dBegin, dEnd);

        Date sDate = sdf.parse(df.format(d));
        System.out.println("String类型转Date类型 " + sDate); //要实现日期+1 需要String转成Date类型
        Format f = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Date结束日期:" + f.format(sDate));
        Calendar c = Calendar.getInstance();
        c.setTime(sDate);
        c.add(Calendar.DAY_OF_MONTH, 1);//利用Calendar 实现 Date日期+1天

        sDate = c.getTime();
        //明天的日期
        System.out.println("Date结束日期+1 " + f.format(sDate));//打印Date日期,显示成功+1天

        //xdate.put("xdatenewweek1", df.format(new Date()));
        Map<String, Object> xdatamap = new HashMap<>();
        //获取ump
        map.clear();
        //    获取 table 表
        map.put("sql", "EXEC mes.dbo.[java_proc_KanBan] '" + mapModel.get("ProductModel") + "' , '" + df.format(d) + "'");
        List<Map<String, Object>> kanbanlist = checkuser.serverFnRunSql(map);
        map.clear();
        //获取最大联动
        map.put("sql", "exec mes.dbo.proc_KanBanContinuousQualifiedCount '" + mapModel.get("ProductModel") + "' , '" + df.format(d) + "'");
        List<Map<String, Object>> orvercount = checkuser.serverFnRunSql(map);
        json.put("model", kanbanlist);
        //获取直通率
        map.clear();//当天的
        map.put("sql", "EXEC mes.dbo.proc_KanBan_Get_Cqy'" + mapModel.get("ProductModel") + "','" + df.format(d) + "'");
        List<Map<String, Object>> listfty = checkuser.serverFnRunSql(map);
        json.put("listfty", listfty);
        //
        //计算前三月 x轴数据
        xdatamap.put("xdatemonth3", xdate.get("xdatemonth3"));
        xdatamap.put("xdatemonth2", xdate.get("xdatemonth2"));
        xdatamap.put("xdatemonth1", xdate.get("xdatemonth1"));
        xdatamap.put("xdateweek3", xdate.get("xdateweek3"));
        xdatamap.put("xdateweek2", xdate.get("xdateweek2"));
        xdatamap.put("xdateweek1", xdate.get("xdateweek1"));
        xdatamap.put("xdatenewweek1", xdate.get("xdatenewweek1"));
        xdatamap.put("xdatenewweek2", xdate.get("xdatenewweek2"));
        xdatamap.put("xdatenewweek3", xdate.get("xdatenewweek3"));
        xdatamap.put("xdatenewweek4", xdate.get("xdatenewweek4"));
        xdatamap.put("xdatenewweek5", xdate.get("xdatenewweek5"));
        xdatamap.put("xdatenewweek6", xdate.get("xdatenewweek6"));
        xdatamap.put("xdatenewweek7", xdate.get("xdatenewweek7"));
        json.put("xdatemap", xdatamap);

        //当周数据
        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-04-01','2020-06-01'");
        List<Map<String, Object>> xdatenewweek1 = checkuser.serverFnRunSql(map);
        map.clear();
        json.put("xdatenewweek1", xdatenewweek1);

        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-04-01','2020-06-01'");
        List<Map<String, Object>> xdatenewweek2 = checkuser.serverFnRunSql(map);
        map.clear();
        json.put("xdatenewweek2", xdatenewweek2);


        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-04-01','2020-06-01'");
        List<Map<String, Object>> xdatenewweek3 = checkuser.serverFnRunSql(map);
        map.clear();
        json.put("xdatenewweek3", xdatenewweek3);

        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-04-01','2020-06-01'");
        List<Map<String, Object>> xdatenewweek4 = checkuser.serverFnRunSql(map);
        map.clear();
        json.put("xdatenewweek4", xdatenewweek4);

        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-04-01','2020-06-01'");
        List<Map<String, Object>> xdatenewweek5 = checkuser.serverFnRunSql(map);
        map.clear();
        json.put("xdatenewweek5", xdatenewweek5);

        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-04-01','2020-06-01'");
        List<Map<String, Object>> xdatenewweek6 = checkuser.serverFnRunSql(map);
        map.clear();
        json.put("xdatenewweek6", xdatenewweek6);

        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-04-01','2020-06-01'");
        List<Map<String, Object>> xdatenewweek7 = checkuser.serverFnRunSql(map);
        map.clear();
        json.put("xdatenewweek7", xdatenewweek7);


//      StringBuilder
        //yfty 直通率
        map.clear();
        //前三月直通率
        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-04-01','2020-06-01'");
        List<Map<String, Object>> xdatemonth3 = checkuser.serverFnRunSql(map);
        json.put("xdatemonth3", xdatemonth3);
        map.clear();
        //前二月直通率
        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-05-01','2020-06-01'");
        List<Map<String, Object>> xdatemonth2 = checkuser.serverFnRunSql(map);
        json.put("xdatemonth2", xdatemonth2);
        map.clear();
        //前1月直通率
        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-06-01','2020-07-01'");
        List<Map<String, Object>> xdatemonth1 = checkuser.serverFnRunSql(map);
        json.put("xdatemonth1", xdatemonth1);
        //前3周
        map.clear();
        //前3周直通率
        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-06-22','2020-06-28'");
        List<Map<String, Object>> xdateweek3 = checkuser.serverFnRunSql(map);
        json.put("xdateweek3", xdateweek3);
        //前2周
        map.clear();
        //前2周直通率
        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-06-29','2020-07-05'");
        List<Map<String, Object>> xdateweek2 = checkuser.serverFnRunSql(map);
        json.put("xdateweek2", xdateweek2);
        //前1周
        map.clear();
        //前1周直通率
        map.put("sql", "EXEC mes.dbo.KanBan_Get_MothweekCqyFtyDt'" + mapModel.get("ProductModel") + "','2020-07-11','2020-07-12'");
        List<Map<String, Object>> xdateweek1 = checkuser.serverFnRunSql(map);
        json.put("xdateweek1", xdateweek1);

        json.put("orvercount", orvercount);
        json.put("code", "kanbanlist");
        return json;
    }
}
