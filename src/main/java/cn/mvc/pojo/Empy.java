package cn.mvc.pojo;

public class Empy {
    private String EmpyCard;    //卡号
    private String FacNo;       //厂别
    private String EmpyNo;      //工号
    private String EmpyName;    //姓名
    private String DeptName;    //部门
    private String EmpyTel;     //电话
    private String NativeNo;    //籍贯
    private String EmpyAddre;   //地址
    private String Sex;         //性别
    private String DutyNoA;     //职称
    private String DutyNoC;     //职等
    private String WorkGroup;   //班别
    private String WorkAddre;   //工作地点
    private String count;  //行数

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getEmpyCard() {
        return EmpyCard;
    }

    public void setEmpyCard(String empyCard) {
        EmpyCard = empyCard;
    }

    public String getFacNo() {
        return FacNo;
    }

    public void setFacNo(String facNo) {
        FacNo = facNo;
    }

    public String getEmpyNo() {
        return EmpyNo;
    }

    public void setEmpyNo(String empyNo) {
        EmpyNo = empyNo;
    }

        public String getEmpyName() {
            if (EmpyName==null){
                return "";
            }
        return EmpyName;
    }

    public void setEmpyName(String empyName) {
        EmpyName = empyName;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getEmpyTel() {
        return EmpyTel;
    }

    public void setEmpyTel(String empyTel) {
        EmpyTel = empyTel;
    }

    public String getNativeNo() {
        return NativeNo;
    }

    public void setNativeNo(String nativeNo) {
        NativeNo = nativeNo;
    }

    public String getEmpyAddre() {
        return EmpyAddre;
    }

    public void setEmpyAddre(String empyAddre) {
        EmpyAddre = empyAddre;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getDutyNoA() {
        return DutyNoA;
    }

    public void setDutyNoA(String dutyNoA) {
        DutyNoA = dutyNoA;
    }

    public String getDutyNoC() {
        return DutyNoC;
    }

    public void setDutyNoC(String dutyNoC) {
        DutyNoC = dutyNoC;
    }

    public String getWorkGroup() {
        return WorkGroup;
    }

    public void setWorkGroup(String workGroup) {
        WorkGroup = workGroup;
    }

    public String getWorkAddre() {
        return WorkAddre;
    }

    public void setWorkAddre(String workAddre) {
        WorkAddre = workAddre;
    }
}
