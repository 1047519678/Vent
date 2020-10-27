package cn.mvc.controller;
import cn.mvc.pojo.Empy;
import cn.mvc.pojo.ShuttleBox;
import cn.mvc.service.Service_interface_Login;
import cn.mvc.tools.GetPageList;
import cn.mvc.tools.RedisTool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

//渲染
@Controller
//指向的目录包
@RequestMapping("")

public class ControllerApiRule {
    @Autowired
    private Service_interface_Login checkuser;

    /***
     * 用户登录判断
     * 返回错误类型
     * 返回成功标识
     * 存入Session
     *
     * */
//用户登录判断
    @RequestMapping(value = "/loginmian")
//返回字符串
    @ResponseBody
    public JSONObject loginmian(HttpServletRequest request, @RequestBody Map<String, Object> map) throws UnsupportedEncodingException {
        String username = new String(map.get("username").toString().getBytes("iso-8859-1"), "UTF-8");
        String password = new String(map.get("password").toString().getBytes("iso-8859-1"), "UTF-8");
        username = username.toUpperCase();
//        检查用户是否有注册
        JSONObject json = new JSONObject();
        List<Empy> code = checkuser.newuser(username);
        if (code.iterator().hasNext()) {
//判断用户是否有注册过
            if (!("".equals(code.iterator().next().getEmpyName()))) {
// 解码
// byte[] base64decodedBytes = Base64.getDecoder().decode(password);
                List<Empy> value = checkuser.checkuserlog(username, Base64.getEncoder().encodeToString(password.getBytes("utf-8")));

                if (value.iterator().hasNext()) {
                    HttpSession session = request.getSession();
//存入session
                    session.setAttribute("EmpyNo", username);
                    session.setAttribute("password", password);
                    session.setAttribute("user", code.iterator().next().getEmpyName());
//登录成功 返回code
                    json.put("code", "loginsucceed");
//用户权限返回用户
                    return json;
                }
//登录失败
                json.put("code", "用户密码不正确");

            } else {
                json.put("code", "账号不存在，请注册账号");
            }
        } else {

            json.put("code", "账号不存在，请注册账号");
        }
        return json;
    }

    //用户登录及跳转验证
    @RequestMapping(value = "/autoRedirect")
    public String loginAndRedirect(HttpServletRequest request, String openId, String Url) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        boolean boo = false;
        if (session.getAttribute("EmpyNo") != null) {
            boo = true;
        } else {
            if (openId != null || !"".equals(openId)) {
                Empy empy = checkuser.getEmpy(openId);
                session.setAttribute("EmpyNo", empy.getEmpyName());
                session.setAttribute("user", empy.getEmpyNo());
                boo = true;
            }
        }
        if (boo) {
            //考勤推送
            if ("kqstate".equals(Url)) {
                return "redirect:/Controllerkqstate";
            }
            //未绑定微信
            if ("notwechat".equals(Url)) {
                return "redirect:/Controllernotwechatstate";
            }
            //未刷卡
            if ("kqemail".equals(Url)) {
                return "redirect:/Controllerkqemailstate";
            }
            //体温异常推送
            if ("demail".equals(Url)) {
                return "redirect:/Controllerdemailstate";
            }
            //公告推送
            if ("Notice".equals(Url)) {
                return "redirect:/ControllerNotice";
            }

            //体温异常推送
            if ("tdpush".equals(Url)) {
                return "redirect:/Controllertdpush";
            }
            //行程异常推送报表
            if ("trip".equals(Url)) {
                return "redirect:/Controllertripstate";
            }
        }
        return "redirect:/login";
    }


    /**
     * 进入主页
     * 判断session是否登录
     * 无回调登录页面
     * 有加载用户权限并打开页面
     **/
//用户登录判断
    @RequestMapping(value = "/phihongmain")
    public String phihongmain(HttpServletRequest request, Model mo) throws UnsupportedEncodingException {
//检查用户是否有注册
        try {
            HttpSession session = request.getSession();
            String user = session.getAttribute("user").toString();
            String username = session.getAttribute("EmpyNo").toString();
            String password = session.getAttribute("password").toString();
            List<Empy> code = checkuser.newuser(username);
            if (code.iterator().hasNext()) {
//判断用户是否有注册过
                if (!("".equals(code.iterator().next().getEmpyName()))) {

                    List<Empy> value = checkuser.checkuserlog(username, Base64.getEncoder().encodeToString(password.getBytes("utf-8")));

                    if (value.iterator().hasNext()) {
//登录成功 返回code
//用户权限返回用户
                        mo.addAttribute("user", user);
                        mo.addAttribute("username", username);
                        mo.addAttribute("password", password);
                        return "index";
                    }
//登录失败

                }
            }
        } catch (Exception ex) {

        }
//找不到session 返回登录页面
        return "default";
//return "redirect:phihongmainindex?username="+username+"&password="+password;
    }

    /**
     * 用户注册
     * 检查用户是否注册过
     * 检验工号在主数据库是否存在
     * 校验身份证后六位是否正确
     * 成功注册
     **/
    @RequestMapping(value = "/reguser")
//返回字符串
    @ResponseBody
    public JSONObject reguser(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException {
//获取到用户注册的号码
        String EmpyNo = map.get("username").toString();
        String password = map.get("password").toString();
        EmpyNo = EmpyNo.toUpperCase();
        JSONObject json = new JSONObject();
        //        检查用户是否有注册
        List<Empy> code = checkuser.newuser(EmpyNo);
        if (!code.iterator().hasNext()) {
            //校验工号在主数据库是否存在
            code = checkuser.checkuserindb(EmpyNo);
            if (code.iterator().hasNext()) {
                if ("".equals(code.iterator().next().getEmpyName())) {
                    json.put("code", "用户在主数据库中不存在，请联系IT或HR 更新数据库");
                } else {
                    //校验身份证后6位是否正确
                    code = checkuser.checkempyid(EmpyNo, password);
                    if (code.iterator().hasNext()) {
                        if ("".equals(code.iterator().next().getEmpyName())) {
                            json.put("code", "身份证后六位输入错误，请输入正确的身份证后六位");
                        } else {
                            //校验成功 注册用户
                            if (checkuser.reguser(EmpyNo, code.iterator().next().getEmpyName(), Base64.getEncoder().encodeToString(password.getBytes("utf-8")), Base64.getEncoder().encodeToString(EmpyNo.getBytes("utf-8")))) {

                                json.put("code", "regsucceed");
                            } else {
                                json.put("code", "注册失败");
                            }

                        }
                    } else {

                        json.put("code", "检验身份证后六位发生错误");
                    }

                }

            } else {


                json.put("code", "检验主数据库时发生错误的呼叫");
            }


        } else {
//判断用户是否有注册过
            json.put("code", "用户已经注册过，请登录");

        }

        return json;
    }

    /*
     * @Description:      * 分配用户权限 * 接受用户账号 * 接受用户密码
     * @Param: [request, map]
     * @return: java.lang.String
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/userauthority", produces = "text/plain;charset=UTF-8")
//返回字符串
    @ResponseBody
    public String userauthority(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        List<Map<String, Object>> dataList = new ArrayList<>();
        try {
//取得当前用户登录信息
            HttpSession session = request.getSession();
            String username = session.getAttribute("EmpyNo").toString();
            map.put("EmpyNo", username);
//获取对应权限
//转成json
            List<Map<String, Object>> autothority = checkuser.userauthority(map);
            List<Map<String, Object>> autothority1 = checkuser.userauthority(map);
            List<Map<String, Object>> maptest = removeRepeatMapByKey(autothority, "gtitle");
            if (maptest.iterator().hasNext()) {
                for (Map<String, Object> map1 : maptest) {
                    List<Map<String, Object>> dataOne = new ArrayList<>();
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("title", map1.get("gtitle"));
                    dataMap.put("href", map1.get("ghref"));
                    dataMap.put("icon", map1.get("gicon"));
                    dataMap.put("spread", map1.get("gspread"));
                    dataMap.put("ischeck", map1.get("gischeck"));
                    dataMap.put("fontFamily", map1.get("gfontfamily"));
                    if (autothority1.iterator().hasNext()) {
                        for (Map<String, Object> map2 : autothority1) {
                            Map<String, Object> dataMap1 = new HashMap<>();
                            if (map1.get("gtitle").equals(map2.get("gtitle"))) {
                                dataMap1.put("title", map2.get("title"));
                                dataMap1.put("href", map2.get("href"));
                                dataMap1.put("icon", map2.get("icon"));
                                dataMap1.put("spread", map2.get("spread"));
                                dataMap1.put("fontFamily", map2.get("fontfamily"));
                                dataOne.add(dataMap1);
                            }
                        }
                    }
                    dataMap.put("children", dataOne);
                    dataList.add(dataMap);
                }
            }
        } catch (Exception ex) {

        }
        return JSON.toJSONString(dataList);
    }

    /*
     * @Description: list 去重复
     * @Param: [list, mapKey]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    public static List<Map<String, Object>> removeRepeatMapByKey(List<Map<String, Object>> list, String mapKey) {

//把list中的数据转换成msp,去掉同一id值多余数据，保留查找到第一个id值对应的数据
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Map> msp = new HashMap<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            Map map = list.get(i);
            String id = (String) map.get(mapKey);
            map.remove(mapKey);
            msp.put(id, map);
        }
//把msp再转换成list,就会得到根据某一字段去掉重复的数据的List<Map>
        Set<String> mspKey = msp.keySet();
        for (String key : mspKey) {
            Map newMap = msp.get(key);
            newMap.put(mapKey, key);
            listMap.add(newMap);
        }
        return listMap;
    }


    /*
     * @Description: 异常分析表
     * @Param: [page, limit, request, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/exception", produces = "application/json")
    @ResponseBody
    public JSONObject exception(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("starttime", request.getParameter("startTime"));
        map.put("endtime", request.getParameter("endTime"));
        map.put("ProductModel", request.getParameter("model"));
        return GetPageList.pageInfo(checkuser.serverexception(map), page, limit);
    }


    /**
     * @Description: 左侧菜单组管理
     * @Param: [page, limit, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/urlgroup", produces = "application/json")
    @ResponseBody
    public JSONObject urlgroup(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> urlgroup = checkuser.server_urlgroup(map);
        return GetPageList.pageInfo(urlgroup, page, limit);
    }

    /**
     * @Description: 左侧菜单组管理
     * @Param: [page, limit, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/urlmanage", produces = "application/json")

    @ResponseBody
    public JSONObject urlmanage(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> url_manage = checkuser.server_urlmanage(map);
        return GetPageList.pageInfo(url_manage, page, limit);
    }

    /**
     * @Description: 上班时长
     * @Param: [page, limit, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/queryworktime", produces = "application/json")
    @ResponseBody

    public JSONObject queryworktime(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> api_queryworktime = checkuser.server_queryworktime(map);
        return GetPageList.pageInfo(api_queryworktime, page, limit);
    }

    /**
     * @Description: 用户修改密码
     * @Param: [request, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/changepwd")
    @ResponseBody
    public JSONObject changepwd(HttpServletRequest request, @RequestBody Map<String, Object> map) throws UnsupportedEncodingException {
//检查用户是否有注册
        JSONObject json = new JSONObject();
        json.put("code", "修改密码失败");
        try {

            HttpSession session = request.getSession();
            String username = session.getAttribute("EmpyNo").toString();
            String password = session.getAttribute("password").toString();
            String oldpassword = new String(map.get("oldpassword").toString().getBytes("iso-8859-1"), "UTF-8");
            String newpassword = new String(map.get("newpassword").toString().getBytes("iso-8859-1"), "UTF-8");
            if (password.equals(oldpassword)) {

                if (checkuser.server_changepwd(username, Base64.getEncoder().encodeToString(newpassword.getBytes("utf-8")))) {
                    try {
                        request.getSession().removeAttribute("user");
                        request.getSession().removeAttribute("EmpyNo");
                        request.getSession().removeAttribute("password");

                    } catch (Exception ex) {


                    }
                    json.put("code", "changesucceed");
                } else {
                    json.put("code", "修改密码失败");

                }

            } else {

                json.put("code", "旧密码错误");
            }
        } catch (Exception ex) {
            json.put("code", ex.getMessage());
        }
        return json;
    }


    /**
     * @Description: 左侧菜单组管理
     * @Param: [page, limit, request, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/api_repair", produces = "application/json")
    @ResponseBody
    public JSONObject api_repair(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("starttime", request.getParameter("startTime"));
        map.put("endtime", request.getParameter("endTime"));
        map.put("line", request.getParameter("selectline"));
        List<Map<String, Object>> url_repair = checkuser.server_repair(map);
        return GetPageList.pageInfo(url_repair, page, limit);
    }

    /**
     * @Description: 添加上班时间
     * @Param: [map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/api_addworktime", produces = "application/json")

    @ResponseBody
    public JSONObject api_addworktime(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException {

        JSONObject json = new JSONObject();
        String model = map.get("productmodel").toString();
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        if (checkuser.server_checkworktime(model, df.format(d))) {
            json.put("code", "该机种今日已维护过上班时间");
        } else {
            if (!map.get("upm").equals(null) || "".equals(map.get("upm"))) {

                if (!map.get("attendance_manpower").equals(null) || "".equals(map.get("attendance_manpower"))) {

                    if (!map.get("work_time").equals(null) || "".equals(map.get("work_time"))) {

                        if (!map.get("dt_worktime").equals(null) || "".equals(map.get("dt_worktime"))) {
                            map.put("date", df.format(d) + " " + map.get("modelworktime"));
                            map.put("theoretical_rate", (Integer.parseInt((String) map.get("upm")) * (Integer.parseInt((String) map.get("work_time")) - Integer.parseInt((String) map.get("dt_worktime")))));
                            if (checkuser.server_addworktime(map)) {

                                json.put("code", "worktimesucceed");
                            } else {

                                json.put("code", "插入数据库失败");

                            }

                        } else {

                            json.put("code", "计划时长不能为空");
                        }

                    } else {

                        json.put("code", "上班时长不能为空");
                    }

                } else {

                    json.put("code", "出勤人力不能为空");
                }

            } else {
                json.put("code", "UMP不能为空");
            }

        }
        return json;
    }


    @RequestMapping(value = "/api_querymodelsx", produces = "application/json")
    @ResponseBody
    public JSONObject api_querymodelsx(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("ProductModel", request.getParameter("productmodel"));
        map.put("today", request.getParameter("startTime"));
        List<Map<String, Object>> url_api_querymodelsx = checkuser.server_querymodelsx(map);
        return GetPageList.pageInfo(url_api_querymodelsx, page, limit);
    }

    @RequestMapping(value = "/api_addnotdtexception", produces = "application/json")
    @ResponseBody
    public JSONObject api_addnotdtexception(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        map.put("date", df.format(d));
        if (checkuser.server_checkupm(map) == null) {

            json.put("code", "未维护今天的上班时间,请先维护上班时间，在使用此功能！");
        } else {

            String upm = checkuser.server_checkupm(map).get("UPM").toString();
            if ("".equals(upm) || "null".equals(upm)) {
                json.put("code", "未维护今天的上班时间,请先维护上班时间，在使用此功能！");

            } else {
                if ("".equals(map.get("Flg").toString())) {
                    json.put("code", "影响产能时长不能为空！");

                } else {
                    map.put("dtqty", (Integer.parseInt(upm) * Integer.parseInt(map.get("Flg").toString())));
                    if (checkuser.server_insertnotdtexception(map)) {

                        json.put("code", "notdtsucceed");

                    } else {
                        json.put("code", "插入失败");
                    }

                }

            }
        }
        return json;
    }


    /*****
     * 接受中文会有乱码问题 需要转换一下
     *编码转换
     *
     * ******/
    public String Code_conversion(String str) throws UnsupportedEncodingException {
        str = new String(str.getBytes("iso-8859-1"), "UTF-8");
        return str;
    }

    /**
     * @Description: 用户管理
     * @Param: [page, limit, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/5/15
     */

    @RequestMapping(value = "/apiusermanage", produces = "application/json")
    @ResponseBody
    public JSONObject apiusermanage(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> user = checkuser.server_usermanage(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    /**
     * 查询体温推送人员
     *
     * @param page
     * @param limit
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/apitdpush", produces = "application/json")
    @ResponseBody
    public JSONObject apitdpush(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> user = checkuser.server_tdpush(map);
        return GetPageList.pageInfo(user, page, limit);
    }


    /**
     * @Description:单个体温用户查询
     * @Param: [page, limit, EmpyNo, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/5/14
     */
    @RequestMapping(value = "/aapitdpush", produces = "application/json")
    //返回字符串
    @ResponseBody
    public JSONObject aapitdpush(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, @RequestParam(required = false) String EmpyNo, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("EmpyNo", EmpyNo);
        List<Map<String, Object>> user = checkuser.server_atdpush(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    @RequestMapping(value = "/update_pushstate", produces = "application/json")
    @ResponseBody
    public JSONObject update_pushstate(@RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", "更新推送状态失败");
        if (checkuser.server_pushstate(map.get("table").toString(), map.get("EmpyNo").toString(), map.get("state").toString())) {
            json.put("code", "updatesucceed");
        }
        return json;
    }

    /**
     * @Description:公告推送
     * @Param:
     * @return:
     * @Author: ShengBi
     * @Date: 2020/5/14
     */
    @RequestMapping(value = "/apinoticepush", produces = "application/json")
    @ResponseBody
    public JSONObject apinoticepush(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {

        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> user = checkuser.server_noticepush(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    @RequestMapping(value = "/aapinoticepush", produces = "application/json")
    @ResponseBody
    public JSONObject aapinoticepush(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, @RequestParam(required = false) String EmpyNo, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("EmpyNo", EmpyNo);
        List<Map<String, Object>> user = checkuser.server_anoticepush(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    @RequestMapping(value = "/apitdemailstate", produces = "application/json")
    @ResponseBody
    public JSONObject apitdemailstate(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {

        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> user = checkuser.server_tdemailstate(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    @RequestMapping(value = "/aapitdemailstate", produces = "application/json")
    @ResponseBody
    public JSONObject aapitdemailstate(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, @RequestParam(required = false) String EmpyNo, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("EmpyNo", EmpyNo);
        List<Map<String, Object>> user = checkuser.server_atdemailstate(map);
        return GetPageList.pageInfo(user, page, limit);
    }


    @RequestMapping(value = "/apikqemailstate", produces = "application/json")
    @ResponseBody
    public JSONObject apikqemailstate(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {

        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> user = checkuser.server_kqemailstate(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    @RequestMapping(value = "/aapikqemailstate", produces = "application/json")
    @ResponseBody
    public JSONObject aapikqemailstate(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, @RequestParam(required = false) String EmpyNo, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("EmpyNo", EmpyNo);
        List<Map<String, Object>> user = checkuser.server_akqemailstate(map);
        return GetPageList.pageInfo(user, page, limit);
    }


    @RequestMapping(value = "/apinotwechatstate", produces = "application/json")
    @ResponseBody
    public JSONObject apinotwechatstate(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {

        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> user = checkuser.server_notwechatstate(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    @RequestMapping(value = "/aapinotwechatstate", produces = "application/json")
    @ResponseBody
    public JSONObject aapinotwechatstate(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, @RequestParam(required = false) String EmpyNo, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("EmpyNo", EmpyNo);
        List<Map<String, Object>> user = checkuser.server_anotwechatstate(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    /**
     * @Description: 考勤推送设置
     * @Param: [page, limit, EmpyNo, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/apikqstate", produces = "application/json")
    @ResponseBody
    public JSONObject apikqstate(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {

        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> user = checkuser.server_kqstate(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    /**
     * @Description: 考勤推送设置
     * @Param: [page, limit, EmpyNo, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/aapikqstate", produces = "application/json")
    //返回字符串
    @ResponseBody
    public JSONObject aapikqstate(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, @RequestParam(required = false) String EmpyNo, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("EmpyNo", EmpyNo);
        List<Map<String, Object>> user = checkuser.server_akqstate(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    /**
     * @Description: 行程轨迹异常api
     * @Param: [page, limit, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/apitrip", produces = "application/json")
    @ResponseBody
    public JSONObject apitrip(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> user = checkuser.server_tripstate(map);
        return GetPageList.pageInfo(user, page, limit);
    }

    /**
     * @Description: 行程异常推送
     * @Param: [page, limit, EmpyNo, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping(value = "/aapitrip", produces = "application/json")
    @ResponseBody
    public JSONObject aapitrip(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit, @RequestParam(required = false) String EmpyNo, Map<String, Object> map) throws UnsupportedEncodingException {
        map.put("EmpyNo", EmpyNo);
        List<Map<String, Object>> user = checkuser.server_atripstate(map);
        return GetPageList.pageInfo(user, page, limit);
    }


    /**
     * @Description: 行程异常推送报表
     * @Param: [model, map]
     * @return: java.lang.String
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/Controlleradduser")
    public String Controlleradduser(Model model, Map<String, Object> map) {
        map.put("starttime", "2020-04-02");
        List<Map<String, Object>> usergroup = checkuser.server_usergroupmanage(map);
        model.addAttribute("usergroup", usergroup);
        return "adduser";
    }

    /**
     * @Description: 更新机种下限
     * @Param: [map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/apiupdate_ul_ll")
    @ResponseBody
    public JSONObject apiupdate_ul_ll(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        if (checkuser.server_update_ul_ll(map)) {
            json.put("code", "updateul_llsucceed");
        } else {

            json.put("code", "修改上下限失败");
        }
        return json;
    }

    /**
     * @Description: 获取机种产能
     * @Param: [request, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/api_modelrate")
    @ResponseBody
    public JSONObject api_modelrate(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {

        JSONObject json = new JSONObject();
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        map.put("today", df.format(d));
        map.put("productmodel", request.getParameter("productmodel"));
        List<Map<String, Object>> datalist = checkuser.server_modelrate(map);
        json.put("data", datalist);
        json.put("code", 0);
        json.put("", "");
        return json;
    }

    /**
     * @Description: 获取用户权限列表
     * @Param: [userId]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/showListAuth")
    @ResponseBody
    public JSONObject showListAuth(String userId) {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> userList = checkuser.getAllAuth();
        List<String> str = checkuser.getUserAuth(userId);
        jo.put("result", userList);
        jo.put("values", str);
        jo.put("userId", userId);
        return jo;
    }

    /**
     * @Description: 添加权限类别
     * @Param: [data, userId]
     * @return: java.lang.String
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/addUserAuth")
    @ResponseBody
    public String addUserAuth(String data, String userId) {
        JSONArray jr = JSONArray.parseArray(data);
        List<ShuttleBox> list = jr.toJavaList(ShuttleBox.class);
        checkuser.addUserAuth(list, userId);
        System.out.println(list);
        return "";
    }

    /**
     * @Description: 删除权限
     * @Param: [data, userId]
     * @return: java.lang.String
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/delUserAuth")
    @ResponseBody
    public String delUserAuth(String data, String userId) {
        JSONArray jr = JSONArray.parseArray(data);
        List<ShuttleBox> list = jr.toJavaList(ShuttleBox.class);
        checkuser.delUserAuth(list, userId);
        System.out.println(list);
        return "";
    }

    /*
     * @Description:  Fromdata 提交 上传保存 更换头像
     * @Param: [request, response, session]
     * @return: java.lang.String
     * @Author: ShengBi
     * @Date: 2020/5/19
     */
    @RequestMapping("/apiupdateimg")
    @ResponseBody
    public JSONObject apiupdateimg(MultipartFile file, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String userId = request.getParameter("userId");
        String path = ("C:\\uploadTemp\\");
        String fileName = userId + ".png";
        try {
            File dir = new File(path, fileName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file.transferTo(dir);
            json.put("code", 0);
            json.put("msg", "success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * @Description: 获取用户头像
     * @Param: [userId, response, request]
     * @return: void
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/apigetimg")
    @ResponseBody
    public void apigetimg(String userId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            userId = (String) request.getSession().getAttribute("EmpyNo");
        } catch (Exception ex) {

        }
        String filePath = "C:\\uploadTemp\\" + userId + ".png";
        String defaultPath = "C:\\uploadTemp\\error.png";
        File file = new File(filePath);
        if (!file.exists()) {
            file = new File(defaultPath);
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
        byte[] bs = new byte[1024];
        int len = 0;
        response.reset();
        response.setContentType("image/png");
        response.setHeader("Content-Length", "" + file.length());
        OutputStream out = response.getOutputStream();
        while ((len = br.read(bs)) > 0) {
            out.write(bs, 0, len);
        }
        out.flush();
        out.close();
        br.close();
    }

    /**
     * @Description: 获取站别与机种
     * @Param: [request, map]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/api_getworkstation")
    @ResponseBody
    public JSONObject api_getworkstation(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {

        JSONObject json = new JSONObject();
        map.put("sql", "SELECT ProductModel,workstation FROM mes.dbo.LineBaseData WHERE IsDataFromOutputStation='1' AND ProductModel='" + request.getParameter("productmodel") + "'");
        List<Map<String, Object>> datalist = checkuser.serverFnRunSql(map);
        json.put("data", datalist);
        json.put("code", 0);
        json.put("", "");
        return json;
    }

    /**
     * @Description: dt 异常时间段异常原因描述
     * @Param: [map, sqlmap]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/api_updateExhappedTime")
    @ResponseBody
    public JSONObject api_updateExhappedTime(@RequestBody Map<String, Object> map, Map<String, Object> sqlmap, Map<String, Object> upm) {
        JSONObject json = new JSONObject();
        String timeflag = map.get("exTime").toString();
        String exlog = map.get("exlog").toString();
        String model = map.get("frm2productmodel").toString();
        String workstation = map.get("frm2workstation").toString();
        String happedTime = map.get("happedTime").toString() + ":00";
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        //获取ump
        upm.put("sql", "SELECT a.upm as upm FROM MES.DBO.Production_report a WHERE a.ProductModel='" + model + "' AND   CONVERT(varchar(100), a.[Date], 23)='" + df.format(d) + "'");
        List<Map<String, Object>> upmlist = checkuser.serverFnRunSql(upm);
        if (upmlist.size() > 0) {
            int intup=(Integer) upmlist.get(0).get("upm");
            intup=intup*Integer.parseInt(timeflag);
            sqlmap.put("sql", "UPDATE MES.DBO.ActualOutput2 SET dtqty='"+ intup +"',  timeflag='" + timeflag + "',exceptionremark='" + exlog + "'  WHERE ProductModel='" + model + "' AND WorkStation='" + workstation + "' AND [Time]='" + happedTime + "' AND DATE='" + df.format(d) + "'   SELECT @@rowcount as count");
            List<Map<String, Object>> datalist = checkuser.serverFnRunSql(sqlmap);
            if (datalist.size() > 0) {
                if ((Integer) datalist.get(0).get("count") > 0) {
                    json.put("code", "update_extimesucceed");
                } else {
                    json.put("code", "维护异常失败，请检查该时间段是否有生产");
                }
            } else {
                json.put("code", "错误的操作");
            }
        } else {

            json.put("code", "没有维护上班时间,请维护后在使用！！！");
        }
        return json;
    }

    /**
     * @Description: 维修达成率 echars饼图报表
     * @Param: [map, sqlmap]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/api_repairuiparm")
    @ResponseBody
    public JSONObject api_repairuiparm(@RequestBody Map<String, Object> map, Map<String, Object> sqlmap) {
        JSONObject json = new JSONObject();
        sqlmap.put("sql", "EXEC MES.DBO.Repair_chart '" + map.get("startTime") + "','" + map.get("endTime") + "','" + map.get("selectline") + "','" + map.get("productmodel") + "'");
        List<Map<String, Object>> datalist = checkuser.serverFnRunSql(sqlmap);
        if (datalist.size() > 1) {
            json.put("code", "repair");
            json.put("PASSCOUNT", datalist.get(0).get("Count"));
            json.put("FAILCOUNT", datalist.get(1).get("Count"));
        } else {
            json.put("PASSCOUNT", "0");
            json.put("FAILCOUNT", "0");
            json.put("code", "repair");
        }
        return json;
    }

    /***
     * @Description: 时间同步主机监控
     * @Param: [page, limit]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    @RequestMapping("/api_redistime")
    @ResponseBody
    public JSONObject api_pc(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(required = false) String limit) {
        RedisTool rt = new RedisTool();
        List<Map<String, Object>> relist = rt.getinfo();
        return GetPageList.pageInfo(relist, page, limit);
    }
}
