package com.system.test.hr.bl.interfaces.pojo;
import java.util.*;
import java.math.*;
public interface EmployeeInterface extends Comparable<EmployeeInterface>,java.io.Serializable
{
    public void setEmployeeId(String employeeId);
    public String getEmployeeId();
    public void setName(String name);
    public String getName();
    public void setDesignation(DesignationInterface designation);
    public DesignationInterface getDesignation();
    public void setDateOfBirth(Date dateOfBirth);
    public Date getDateOfBirth();
    public void setGender(char gender);
    public char getGender();
    public void setIsIndian(boolean isIndian);
    public boolean getIsIndian();
    public void setBasicSalary(BigDecimal basicSalary);
    public BigDecimal getBasicSalary();
    public void setPANNumber(String panNumber);
    public String getPANNumber();
    public void setAadharCardNumber(String aadharCardNumber);
    public String getAadharCardNumber();
}