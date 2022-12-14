package mhl.domain;

import sun.applet.AppletResourceLoader;

/**
 * this javabean reflects the employee table
 */
public class Employee {
    private Integer id;
    private String empId;
    private String pwd;
    private String name;
    private String job;

    public Employee() { // apache-dbutils needs this default constructor 
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", empId='" + empId + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getId() {
        return id;
    }

    public String getEmpId() {
        return empId;
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
