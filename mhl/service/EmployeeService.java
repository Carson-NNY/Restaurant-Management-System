package mhl.service;

import mhl.dao.EmployeeDAO;
import mhl.domain.Employee;

/**
 * This class performs various operations on the employee table (by calling the EmployeeDao object)
 */
public class EmployeeService {

    // Define an EmployeeDao property
    EmployeeDAO employeeDAO=  new EmployeeDAO();

    // Returns an Employee object based on empId and pwd
    // If there is no this object, return null
    public Employee getEmployeeByIdAndPwd(String empId,String pwd){
        Employee employee = employeeDAO.querySingle("select * from employee where empId=? and pwd = md5(?)", Employee.class, empId, pwd);
        return employee;
    }

}
