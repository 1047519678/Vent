package cn.mvc.controller;

import cn.mvc.service.Service_interface_Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

//渲染
@Controller
//指向的目录包
@RequestMapping("")

public class ControllerRedirectURLRule {
    @Autowired
    public Service_interface_Login checkuser;


    //注册用户页面
    @RequestMapping("/loginreg")
    public String loginreg() {
        return "reguser";
    }

    //忘记密码页面
    @RequestMapping("/loginforget")
    public String loginforget() {
        return "forget";
    }

    //異常分析表
    @RequestMapping("/exceptionjsp")
    public String exception(Model model, Map<String, Object> map) {
        map.put("model", "model");
        List<Map<String, Object>> addmodel = checkuser.server_getmodel(map);
        model.addAttribute("model", addmodel);
        return "exception";
    }

    //列表组管理
    @RequestMapping("/controllerurlgroup")
    public String controllerurlgroup() {
        return "UrlGroup";
    }

    //列表组管理
    @RequestMapping("/controllerurlmanage")
    public String controllerurlmanage() {
        return "Urlmanage";
    }

    //初始化页面登录页面
    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        try {
            request.getSession().removeAttribute("user");
            request.getSession().removeAttribute("EmpyNo");
            request.getSession().removeAttribute("password");

        } catch (Exception ex) {

        }
        return "default";
    }

    //打开修改密码页
    @RequestMapping("/controlluserchangepasswod")
    public String controlluserchangepasswod() {
        return "userchangepasswod";
    }

    //24小时未维修报表
    @RequestMapping("/repair")
    public String repair(Model model, Map<String, Object> map) {

        map.put("model", "model");
        List<Map<String, Object>> line_name = checkuser.serverGetLineName(map);
        model.addAttribute("line", line_name);
        return "repair";
    }

    //24小时未维修报表
    @RequestMapping("/repairui")
    public String repairui(Model model, Map<String, Object> map) {

        map.put("model", "model");
        List<Map<String, Object>> line_name = checkuser.serverGetLineName(map);
        List<Map<String, Object>> addmodel = checkuser.server_getreparimodel(map);
        model.addAttribute("model", addmodel);
        model.addAttribute("line", line_name);
        return "repairui";
    }

    //引导页
    @RequestMapping("/console")
    public String console() {
        return "console";
    }

    //上班时间维护
    @RequestMapping("/worktime")
    public String worktime() {
        return "r_worktime";
    }

    //添加上班时间
    @RequestMapping("/addworktime")
    public String addworktime(Model model, Map<String, Object> map) {
        map.put("model", "model");
        List<Map<String, Object>> addmodel = checkuser.server_getmodel(map);
        model.addAttribute("model", addmodel);
        return "addworktime";
    }


    //看板上下限设置
    @RequestMapping("/kanbansx")
    public String kanbansx(Model model, Map<String, Object> map) {
//        Model model,Map<String,Object> map
        map.put("model", "model");
        List<Map<String, Object>> addmodel = checkuser.server_getmodel(map);
        model.addAttribute("model", addmodel);

        return "KanBansx";
    }


    //看板设施DT设置
    @RequestMapping("/dtexception")
    public String dtexception(Model model, Map<String, Object> map) {

        map.put("model", "model");
        List<Map<String, Object>> addmodel = checkuser.server_getmodel(map);
        model.addAttribute("model", addmodel);

        return "dtexception";
    }

    //看板非设施DT设置
    @RequestMapping("/notdtexception")
    public String notdtexception(Model model, Map<String, Object> map) {
        map.put("model", "model");
        List<Map<String, Object>> addmodel = checkuser.server_getmodel(map);
        model.addAttribute("model", addmodel);
        return "notdtexception";
    }

    //添加非dt异常
    @RequestMapping("/addnotdtexception")
    public String addnotdtexception(Model model, Map<String, Object> map) {

        map.put("model", "model");
        List<Map<String, Object>> addmodel = checkuser.server_getmodel(map);
        model.addAttribute("model", addmodel);
        return "addnotdtexception";
    }


    //添加非dt异常
    @RequestMapping("/kanban")
    public String kanban() {
        return "kanban";
    }

    //添加非dt异常
    @RequestMapping("/Qrcode")
    public String Qrcode() {
        return "Qr_code";
    }

    //用户管理列表
    @RequestMapping("/Controllerusermanage")
    public String Controllerusermanage() {
        return "usermanage";
    }

    //体温异常推送
    @RequestMapping("/Controllertdpush")
    public String Controllertdpush() {
        return "tdpush";
    }

    //公告推送
    @RequestMapping("/ControllerNotice")
    public String ControllerNotice() {
        return "Notice";
    }

    //体温异常推送
    @RequestMapping("/Controllerdemailstate")
    public String Controllerdemailstate() {
        return "demail";
    }

    //未刷卡
    @RequestMapping("/Controllerkqemailstate")
    public String Controllerkqemailstate() {
        return "kqemailstate";
    }

    //未绑定微信
    @RequestMapping("/Controllernotwechatstate")
    public String Controllernotwechatstate() {
        return "notwechatstate";
    }

    //考勤
    @RequestMapping("/Controllerkqstate")
    public String Controllerkqstate() {
        return "kqstate";
    }

    //用户组管理
    @RequestMapping("/Controllerusergroup")
    public String Controllerusergroup() {
        return "usergroup";
    }

    //用户组管理
    @RequestMapping("/Controllerapimanage")
    public String Controllerapimanage() {
        return "apimanage";
    }


    //行程异常推送报表
    @RequestMapping("/Controllertripstate")
    public String Controllertripstate() {
        return "trip";
    }


    //更新用户头像
    @RequestMapping("/Controllerimg")
    public String Controllerimg(HttpServletRequest request, Model mo) {
        try {
            mo.addAttribute("EmpyNo", request.getSession().getAttribute("EmpyNo"));

        } catch (Exception ex) {

            return "default";
        }
        return "userimage";
    }

    @RequestMapping("/Controllerredistime")
    public String Controllerredistime() {
        return "redistime";
    }

}
