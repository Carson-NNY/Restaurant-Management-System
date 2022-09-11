package mhl.domain;

/**
 * @author Carson
 * @Version
 */
public class EmployeeInfo {
    private Integer id;
    private String empId;
    private String name;
    private String sex;
    private String adress;

    public EmployeeInfo() {
    }

    public Integer getId() {
        return id;
    }

    public String getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getAdress() {
        return adress;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeInfo{" +
                "id=" + id +
                ", empId='" + empId + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", adress='" + adress + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    private String status;
}
