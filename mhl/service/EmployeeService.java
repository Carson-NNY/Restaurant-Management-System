package mhl.service;

import mhl.dao.EmployeeDAO;
import mhl.domain.Employee;

/**
 * @author Carson
 * 该类完成对employee表的各种操作 （通过调用 EmployeeDao 对象完成）
 */
public class EmployeeService {

    // 定义一个EmployeeDao 属性
    EmployeeDAO employeeDAO=  new EmployeeDAO();

    // 根据empId和pwd返回一个Employee对象
    // 如果查询不到，返回null
    public Employee getEmployeeByIdAndPwd(String empId,String pwd){
        Employee employee = employeeDAO.querySingle("select * from employee where empId=? and pwd = md5(?)", Employee.class, empId, pwd);
        return employee;
    }

}
