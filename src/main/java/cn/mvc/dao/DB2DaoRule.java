package cn.mvc.dao;

import cn.mvc.pojo.Empy;
import cn.mvc.tools.DataSource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@DataSource("dataSource2")
public interface DB2DaoRule {
    //判断用户在主数据库是否存在
    public  List<Empy> dbinnewuser(@Param("username") String username);
    //检验身份证
    public  List<Empy> Empyidcheck(@Param("username")String username,@Param("password") String password);
    //体温异常推送
    public  List<Map<String,Object>> dao_tdpush(Map<String,Object> map);

    //体温异常推送 单个查询
    public  List<Map<String,Object>> dao_atdpush(Map<String,Object> map);

    //
    /** 
    * @Description:  修改推送状态
    * @Param:  
    * @return:  
    * @Author: ShengBi 
    * @Date: 2020/5/14 
    */
    public int dao_update_pushstate(@Param("tablecount") String tablecount,@Param("EmpyNo") String EmpyNo,@Param("state") String state);

    /**
    * @Description: 公告推送
    * @Param:
    * @return:
    * @Author: ShengBi
    * @Date: 2020/5/14
    */
    public  List<Map<String,Object>> dao_noticepush(Map<String,Object> map);
    public  List<Map<String,Object>> dao_anoticepush(Map<String,Object> map);


  //   未上传体温报表推送
    public  List<Map<String,Object>> dao_tdemailstate(Map<String,Object> map);
    public  List<Map<String,Object>> dao_atdemailstate(Map<String,Object> map);

   //未刷卡人员信息
    public  List<Map<String,Object>> dao_kqemailstate(Map<String,Object> map);
    public  List<Map<String,Object>> dao_akqemailstate(Map<String,Object> map);

 //未绑定微信 notwechatstate

    public  List<Map<String,Object>> dao_notwechatstate(Map<String,Object> map);
    public  List<Map<String,Object>> dao_anotwechatstate(Map<String,Object> map);

//考勤推送
    public  List<Map<String,Object>> dao_kqstate(Map<String,Object> map);
    public  List<Map<String,Object>> dao_akqstate(Map<String,Object> map);
//异常轨迹报表推送

    public  List<Map<String,Object>> dao_tripstate(Map<String,Object> map);
    public  List<Map<String,Object>> dao_atripstate(Map<String,Object> map);

    @Select("Select w.EmpyNo,w.Empyname as EmpyName from WechatBind w join User_Account a on w.EmpyNo = a.employee_id where w.opid=#{openId} and a.state=1 and a.del =0")
    Empy getEmpy(@Param("openId") String openId);
}
