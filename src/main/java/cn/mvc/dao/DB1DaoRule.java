package cn.mvc.dao;

import cn.mvc.pojo.Empy;
import cn.mvc.tools.DataSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@DataSource("dataSource1")
public interface DB1DaoRule {

    //用户登录判断
    public List<Empy> checkuserlog(@Param("username") String username, @Param("password")  String password);

    //用户注册
    public int reguser(@Param("EmpyNo") String EmpyNo,@Param("EmpyName") String EmpyName,@Param("password") String password,@Param("key") String key);


    //判断用户账号是否存在
    public  List<Empy> newuser(@Param("username") String username);

    //返回用户权限
    public List<Map<String, Object>> userauthority (Map<String, Object> map);

    //返回异常分析报表
    public List<Map<String,Object>> dao_exception(Map<String,Object> map);

     //左侧菜单列表
    public  List<Map<String,Object>> dao_urlgroup(Map<String,Object> map);

    //菜单列表管理
    public List<Map<String,Object>> dao_urlmanage(Map<String,Object>map);
    //修改密码
    public int dao_changepwd(@Param("EmpyNo") String EmpyNo,@Param("pwd") String pwd);

    //24小时维修报表
    public List<Map<String,Object>> dao_repair(Map<String,Object> map);
    //上班时间列表
    public List<Map<String,Object>> dao_queryworktime(Map<String,Object> map);

    //修改密码
    public int dao_checkworktime(@Param("model") String EmpyNo,@Param("date") String pwd);
    //添加上班时间
    public int dao_addworktime(Map<String,Object> map);

    //查询机种上下限
    public List<Map<String,Object>> dao_querymodelsx(Map<String,Object>map);
    //返回
    public  Map<String,Object> dao_checkupm(Map<String,Object>map);
//    @Param("ProductModel") String ProductModel,@Param("date") String date
    //插入非Dt异常
    public int dao_insertnotdtexception (Map<String,Object>map);
    //用户管理
    public  List<Map<String,Object>> dao_usermanage(Map<String,Object> map);
  //用户组管理 usergroupmanage
  public  List<Map<String,Object>> dao_usergroupmanage(Map<String,Object> map);

  //获取机种
  public  List<Map<String,Object>> dao_getmodel(Map<String,Object> map);


    public  List<Map<String,Object>> dao_getreparimodel(Map<String,Object> map);

    //修改机种上下限
    public  int dao_update_ul_ll(Map<String,Object>map);
    //获取机种生产状况
    public  List<Map<String,Object>> dao_modelrate(Map<String,Object> map);

    //通过用户id查询对应权限
    List<String> getUserAuth(@Param("userId") String UserId);

    //查询全部权限信息
    List<Map<String, Object>> getAllAuth();

    //添加用户权限
    void addUserAuth(@Param("urlid") String UrlId, @Param("userId") String userId);

    //通过用户id和权限编号移除用户对应权限
    void delUserAuth(@Param("urlid") String UrlId, @Param("userId") String userId);

//   获取产线
    public  List<Map<String,Object>> DaoGetLineName(Map<String,Object> map);

//    --	<!--	万能的 MAP类型 dao -->
//    <!--个人想法 就同类型传sql 过来就好了 减少代码量	-->
    public  List<Map<String,Object>> DaoFnRunSql(Map<String,Object> map);

    // 每日修改Excel数据
    Map<String , Object> selDataInfo(Map<String, Object> map);
}
