package cn.mvc.service;

import cn.mvc.pojo.Empy;
import cn.mvc.pojo.ShuttleBox;

import java.util.List;
import java.util.Map;

public interface Service_interface_Login {
    //用户登录
    public List<Empy> checkuserlog(String username, String password);

    //用户注册
    public boolean reguser(String EmpyNO, String EmpyName, String password, String key);

    //判断用户是否注册过
    public List<Empy> newuser(String username);

    //判断用户输入的身份证后六位是否正确
    public List<Empy> checkempyid(String username, String password);

    //判断数据工号在主数据库是否存在
    public List<Empy> checkuserindb(String username);

    //获取用户权限
    public List<Map<String, Object>> userauthority(Map<String, Object> map);

    //返回异常分析表
    public List<Map<String, Object>> serverexception(Map<String, Object> map);

    //左侧菜单组管理
    public List<Map<String, Object>> server_urlgroup(Map<String, Object> map);

    //菜单列表管理
    public List<Map<String, Object>> server_urlmanage(Map<String, Object> map);

    //修改用户密码
    public boolean server_changepwd(String EmpyNo, String pwd);

    //24维修报表
    public List<Map<String, Object>> server_repair(Map<String, Object> map);

    //上班时间报表
    public List<Map<String, Object>> server_queryworktime(Map<String, Object> map);

    //上传时间检查
    public boolean server_checkworktime(String model, String date);

    //添加上班时长
    public boolean server_addworktime(Map<String, Object> map);

    //查询机种上下限
    public List<Map<String, Object>> server_querymodelsx(Map<String, Object> map);

    //检查有没有维护upm
    public Map<String, Object> server_checkupm(Map<String, Object> map);

    //插入异常项目内容
    public boolean server_insertnotdtexception(Map<String, Object> map);

    //用户管理
    public List<Map<String, Object>> server_usermanage(Map<String, Object> map);

    //体温异常推送
    public List<Map<String, Object>> server_tdpush(Map<String, Object> map);

    //体温异常推送单个查询
    public List<Map<String, Object>> server_atdpush(Map<String, Object> map);

    //推送状态修改
    public boolean server_pushstate(String tablecount, String EmpyNo, String state);

    //公告异常推送
    public List<Map<String, Object>> server_noticepush(Map<String, Object> map);

    public List<Map<String, Object>> server_anoticepush(Map<String, Object> map);

    //未上传体温报表
    public List<Map<String, Object>> server_tdemailstate(Map<String, Object> map);

    public List<Map<String, Object>> server_atdemailstate(Map<String, Object> map);

    //未刷卡 kqemailstate
    public List<Map<String, Object>> server_kqemailstate(Map<String, Object> map);

    public List<Map<String, Object>> server_akqemailstate(Map<String, Object> map);

    //未绑定微信 notwechatstate
    public List<Map<String, Object>> server_notwechatstate(Map<String, Object> map);

    public List<Map<String, Object>> server_anotwechatstate(Map<String, Object> map);

    // 考勤推送 kqstate
    public List<Map<String, Object>> server_kqstate(Map<String, Object> map);

    public List<Map<String, Object>> server_akqstate(Map<String, Object> map);

    //tripstate
    public List<Map<String, Object>> server_tripstate(Map<String, Object> map);

    public List<Map<String, Object>> server_atripstate(Map<String, Object> map);

    //用户组管理 usergroupmanage
    public List<Map<String, Object>> server_usergroupmanage(Map<String, Object> map);
     //推送页面的opid
    public Empy getEmpy(String openId);

    public List<Map<String, Object>> server_getmodel(Map<String, Object> map);

    public List<Map<String, Object>> server_getreparimodel(Map<String, Object> map);
    //修改机种上下限
    public  boolean server_update_ul_ll(Map<String,Object>map);
    //获取机种生产状况
    public List<Map<String,Object>> server_modelrate(Map<String,Object>map);

    //通过用户id查询对应类别
    List<String> getUserAuth(String userId);

    //查询全部类别信息
    List<Map<String, Object>> getAllAuth();

    //添加用户权限
    void addUserAuth(List<ShuttleBox> list, String userId);

    //移除用户权限
    void delUserAuth(List<ShuttleBox> list, String userId);

    public List<Map<String, Object>> serverGetLineName(Map<String, Object> map);
    //    --	<!--	万能的 MAP类型 dao -->
//    <!--个人想法 就同类型传sql 过来就好了 减少代码量	-->
    public List<Map<String, Object>> serverFnRunSql(Map<String, Object> map);

    // 每日修改Excel数据
    Map<String , Object> getDataInfo(Map<String,Object> map);
}

