package mhl.service;

import mhl.dao.EmployeeInfoDAO;
import mhl.domain.EmployeeInfo;


public class EmployeeInfoService {
    private EmployeeInfoDAO employeeInfoDAO= new EmployeeInfoDAO();

    public EmployeeInfo listEmployeeInfo(String empId){
        return  employeeInfoDAO.querySingle("select * from employeeInfo where empId =?", EmployeeInfo.class, empId);

    }
}
