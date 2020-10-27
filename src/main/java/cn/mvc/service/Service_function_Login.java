package cn.mvc.service;

import cn.mvc.dao.DB1DaoRule;
import cn.mvc.dao.DB2DaoRule;
import cn.mvc.pojo.Empy;
import cn.mvc.pojo.ShuttleBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Service_function_Login implements Service_interface_Login {
    @Autowired
    public DB1DaoRule db1_check;
    @Autowired
    public DB2DaoRule db2_check;

    @Override
    public List<Empy> checkuserlog(String username, String password) {
        List<Empy> demo = db1_check.checkuserlog(username, password);
        return demo;
    }

    //注册用户
    @Override
    public boolean reguser(String EmpyNO,String EmpyName, String password,String key) {

        return db1_check.reguser(EmpyNO, EmpyName,password,key)>0;
    }

    //判断账号是否被注册过
    @Override
    public List<Empy> newuser(String username) {
        List<Empy> count = db1_check.newuser(username);
        return count;
    }
//判断身份证后六位是否正确
    @Override
    public List<Empy> checkempyid(String username, String password) {
        List<Empy> count = db2_check.Empyidcheck(username,password);
        return count;
    }

    @Override
//    判断主数据库中是否有此工号
    public List<Empy> checkuserindb(String username) {
        List<Empy> count = db2_check.dbinnewuser(username);
        return count;
    }


//返回用户权限
@Override
public  List<Map<String, Object>> userauthority(Map<String, Object> map) {

    return db1_check.userauthority(map);
}

//返回异常分析表
    @Override
    public  List<Map<String,Object>> serverexception(Map<String,Object> map){
        return db1_check.dao_exception(map);
    }
//左侧菜单组管理
    @Override
    public List<Map<String, Object>> server_urlgroup(Map<String, Object> map) {
        return db1_check.dao_urlgroup(map);
    }
    //左侧菜单列表管理
    @Override
    public List<Map<String, Object>> server_urlmanage(Map<String, Object> map) {
        return db1_check.dao_urlmanage(map);
    }

    //修改密码
    @Override
    public boolean server_changepwd(String EmpyNo, String pwd) {
        return  db1_check.dao_changepwd(EmpyNo, pwd)>0;
    }

    //24小时维修报表
    @Override
    public List<Map<String, Object>> server_repair(Map<String, Object> map) {
        return db1_check.dao_repair(map);
    }

    @Override
    public List<Map<String, Object>> server_queryworktime(Map<String, Object> map) {
        return db1_check.dao_queryworktime(map);
    }
   //检查是否维护过上班时间
    @Override
    public boolean server_checkworktime(String model, String date) {
        return db1_check.dao_checkworktime(model,date) >0;
    }

    @Override
    public boolean server_addworktime(Map<String, Object> map) {
        return db1_check.dao_addworktime(map)>0;
    }
//    tripstate
    @Override
    public List<Map<String, Object>> server_tripstate(Map<String, Object> map) {
        return db2_check.dao_tripstate(map);
    }
    //用户组管理 usergroupmanage
    @Override
    public List<Map<String, Object>> server_usergroupmanage(Map<String, Object> map) {
        return db1_check.dao_usergroupmanage(map);
    }

    @Override
    public Empy getEmpy(String openId) {
        return db2_check.getEmpy(openId);
    }

    //tripstate
    @Override
    public List<Map<String, Object>> server_atripstate(Map<String, Object> map) {
        return db2_check.dao_atripstate(map);
    }
    //获取机种生产状况
    @Override
    public List<Map<String, Object>> server_modelrate(Map<String, Object> map) {
        return db1_check.dao_modelrate(map);
    }

    //修改机种上下限
    @Override
    public boolean server_update_ul_ll(Map<String, Object> map) {
        return db1_check.dao_update_ul_ll(map)>0;
    }

    @Override
    public List<Map<String, Object>> server_getmodel(Map<String, Object> map) {
        return db1_check.dao_getmodel(map);
    }

    //查询机种上下限
    @Override
    public List<Map<String, Object>> server_querymodelsx(Map<String, Object> map) {
        return db1_check.dao_querymodelsx(map);
    }
   //检查有没有维护UPM
    @Override
    public Map<String, Object> server_checkupm(Map<String, Object> map) {
        return db1_check.dao_checkupm(map);
    }

    //插入异常项目
    @Override
    public boolean server_insertnotdtexception(Map<String, Object> map) {
        return db1_check.dao_insertnotdtexception(map)>0;
    }

    //用户组管理
    @Override
    public List<Map<String, Object>> server_usermanage(Map<String, Object> map) {
        return db1_check.dao_usermanage(map);
    }


    //体温异常推送
    @Override
    public List<Map<String, Object>> server_tdpush(Map<String, Object> map) {
        return db2_check.dao_tdpush(map);
    }
    //未上传体温报表
    @Override
    public List<Map<String, Object>> server_tdemailstate(Map<String, Object> map) {
        return db2_check.dao_tdemailstate(map);
    }
    //未上传体温报表
    @Override
    public List<Map<String, Object>> server_atdemailstate(Map<String, Object> map) {
        return db2_check.dao_atdemailstate(map);
    }
  //未刷卡报表
    @Override
    public List<Map<String, Object>> server_kqemailstate(Map<String, Object> map) {
        return db2_check.dao_kqemailstate(map);
    }
//未绑定微信
    @Override
    public List<Map<String, Object>> server_notwechatstate(Map<String, Object> map) {
        return db2_check.dao_notwechatstate(map);
    }
//未绑定微信
    @Override
    public List<Map<String, Object>> server_anotwechatstate(Map<String, Object> map) {
        return db2_check.dao_anotwechatstate(map);
    }
//..考勤推送
    @Override
    public List<Map<String, Object>> server_kqstate(Map<String, Object> map) {
        return db2_check.dao_kqstate(map);
    }
//考勤推送
    @Override
    public List<Map<String, Object>> server_akqstate(Map<String, Object> map) {
        return db2_check.dao_akqstate(map);
    }

    //    未刷卡报表
    @Override
    public List<Map<String, Object>> server_akqemailstate(Map<String, Object> map) {
        return db2_check.dao_akqemailstate(map);
    }

    @Override
    public List<Map<String, Object>> server_anoticepush(Map<String, Object> map) {
        return db2_check.dao_anoticepush(map);
    }

    //公告异常推送
    @Override
    public List<Map<String, Object>> server_noticepush(Map<String, Object> map) {
        return db2_check.dao_noticepush(map);
    }

    @Override
    public boolean server_pushstate(String tablecount, String EmpyNo, String state) {
        return db2_check.dao_update_pushstate(tablecount,EmpyNo,state)>0;
    }

    @Override
    public List<Map<String, Object>> server_atdpush(Map<String, Object> map) {
        return db2_check.dao_atdpush(map);
    }
    //根据ID获取用户权限
    @Override
    public List<String> getUserAuth(String userId) {
        return db1_check.getUserAuth(userId);
    }

    //获取所有权限
    @Override
    public List<Map<String, Object>> getAllAuth() {
        return db1_check.getAllAuth();
    }

    //添加用户权限
    @Override
    public void addUserAuth(List<ShuttleBox> list, String userId) {
        for (ShuttleBox s : list) {
            db1_check.addUserAuth(s.getValue(), userId);
        }
    }

    //移除用户权限
    @Override
    public void delUserAuth(List<ShuttleBox> list, String userId) {
        for (ShuttleBox s : list) {
            db1_check.delUserAuth(s.getValue(), userId);
        }
    }
    //    --	<!--	万能的 MAP类型 dao -->
//    <!--个人想法 就同类型传sql 过来就好了 减少代码量	-->
    @Override
    public List<Map<String, Object>> serverFnRunSql(Map<String, Object> map) {
        return db1_check.DaoFnRunSql(map);
    }
    @Override
    public List<Map<String, Object>> server_getreparimodel(Map<String, Object> map) {
        return db1_check.dao_getreparimodel(map);
    }

    //获取线别
    @Override
    public List<Map<String, Object>> serverGetLineName(Map<String, Object> map) {
        return db1_check.DaoGetLineName(map);
    }


    @Override
    public Map<String, Object> getDataInfo(Map<String, Object> map) {
        return db1_check.selDataInfo(map);
    }

}
