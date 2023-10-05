package com.system.test.hr.bl.interfaces.managers;
import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.pojo.*;
import java.util.*;
import java.math.*;
public interface EmployeeManagerInterface
{
    public void addEmployee(EmployeeInterface employee) throws BLException;
    public void updateEmployee(EmployeeInterface employee) throws BLException;
    public void removeEmployee(String employeeId) throws BLException;
    public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException;
    public EmployeeInterface getEmployeeByPanNumber(String panNumber) throws BLException;
    public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException;
    public int getEmployeeCount() throws BLException;
    public boolean employeeIdExists(String employeeId) throws BLException;
    public boolean employeePanNumberExists(String panNumber) throws BLException;
    public boolean employeeAadharCardNumberExists(String aadharCardNumber) throws BLException;
    public Set<EmployeeInterface> getEmployees();
    public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode) throws BLException;
    public int getEmployeeCountByDesignationCode(int designationCode) throws BLException;
    public boolean designationAlloted(int designationCode) throws BLException;
}