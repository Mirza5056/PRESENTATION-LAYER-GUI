package com.system.test.hr.bl.managers;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.dl.interfaces.dto.*;
import com.system.test.hr.dl.interfaces.dao.*;
import com.system.test.hr.dl.dao.*;
import com.system.test.hr.dl.dto.*;
import com.system.test.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManager implements EmployeeManagerInterface
{
    private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
    private Map<String,EmployeeInterface> employeePanNumberWiseEmployeesMap;
    private Map<String,EmployeeInterface> employeeAadharCardNumberWiseEmployeesMap;
    private Set<EmployeeInterface> employeesSet;
    private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeeMap;
    private static EmployeeManager employeeManager=null;
    private EmployeeManager() throws BLException
    {
        populateDataStructures();
    }
    private void populateDataStructures() throws BLException
    {
        this.employeeIdWiseEmployeesMap=new HashMap<>();
        this.employeePanNumberWiseEmployeesMap=new HashMap<>();
        this.employeeAadharCardNumberWiseEmployeesMap=new HashMap<>();
        this.designationCodeWiseEmployeeMap=new HashMap<>();
        this.employeesSet=new TreeSet<>();
        try{
            Set<EmployeeDTOInterface> dlEmployees;
            dlEmployees=new EmployeeDAO().getAll();
            EmployeeInterface employee;
            DesignationManagerInterface designationManager;
            designationManager=DesignationManager.getDesignationManager();
            DesignationInterface designation;
            Set<EmployeeInterface> ets;
            for(EmployeeDTOInterface dlEmployee:dlEmployees)
            {
                employee=new Employee();
                employee.setEmployeeId(dlEmployee.getEmployeeId());
                employee.setName(dlEmployee.getName());
                designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
                employee.setDesignation(designation);
                employee.setDateOfBirth(dlEmployee.getDateOfBirth());
                employee.setGender(dlEmployee.getGender());
                employee.setIsIndian(dlEmployee.getIsIndian());
                employee.setBasicSalary(dlEmployee.getBasicSalary());
                employee.setPANNumber(dlEmployee.getPANNumber());
                employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
                this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),employee);
                this.employeePanNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
                this.employeeAadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
                this.employeesSet.add(employee);
                ets=this.designationCodeWiseEmployeeMap.get(designation.getCode());
                if(ets==null)
                {
                    ets=new TreeSet<>();
                    ets.add(employee);
                    designationCodeWiseEmployeeMap.put(new Integer(designation.getCode()),ets);
                }
                else
                {
                    ets.add(employee);
                }
            }
        }catch(DAOException daoException)
        {
            BLException blException=new BLException();
            blException.setGenericException(daoException.getMessage());
            throw blException;
        }
    }
    public static EmployeeManagerInterface getEmployeeManager() throws BLException
    {
        if(employeeManager==null) employeeManager=new EmployeeManager();
        return employeeManager;
    }
    public void addEmployee(EmployeeInterface employee) throws BLException
    {
        BLException blException;
        blException=new BLException();
        String employeeId=employee.getEmployeeId();
        String name=employee.getName();
        DesignationInterface designation=employee.getDesignation();
        int designationCode=0;
        Date dateOfBirth=employee.getDateOfBirth();
        char gender=employee.getGender();
        boolean isIndian=employee.getIsIndian();
        BigDecimal basicSalary=employee.getBasicSalary();
        String panNumber=employee.getPANNumber();
        String aadharCardNumber=employee.getAadharCardNumber();
        if(employeeId!=null)
        {
            employeeId=employeeId.trim();
            if(employeeId.length()>0)
            {
                blException.addException("employeeId","employeeId length is zero");
            }
        }
        if(name==null)
        {
            blException.addException("name","name is null");
        }
        else
        {
            name=name.trim();
            if(name.length()==0)
            {
                blException.addException("name","name length is zero");
            }
        }
        DesignationManagerInterface designationManager;
        designationManager=DesignationManager.getDesignationManager();
        if(designation==null)
        {
            blException.addException("designation","designation is null");
        }
        else
        {
            designationCode=designation.getCode();
            if(designationManager.designationCodeExists(designation.getCode())==false)
            {
                blException.addException("designation","Invalid designation Code");
            }
        }
        if(dateOfBirth==null)
        {
            blException.addException("dateOfBirth","Date Of Birth is null");
        }
        if(gender==' ')
        {
            blException.addException("gender","gender is null");
        }
        if(basicSalary==null)
        {
            blException.addException("basicSalary","Basic Salary is null");
        }
        else
        {
            if(basicSalary.signum()==-1)
            {
                blException.addException("basicSalary","Basic Salary should not be negative");
            }
        }
        if(panNumber==null)
        {
            blException.addException("panNumber","Pan Number is required");
        }
        else
        {
            panNumber=panNumber.trim();
            if(panNumber.length()==0)
            {
                blException.addException("panNumber","pan number length is zero");
            }
        }
        if(aadharCardNumber==null)
        {
            blException.addException("aadharCardNumber","aadhar card number required");
        }
        else
        {
            aadharCardNumber=aadharCardNumber.trim();
            if(aadharCardNumber.length()==0)
            {
                blException.addException("aadharCardNumber","aadhar card number length is zero");
            }
        }
        if(panNumber!=null && panNumber.length()>0)
        {
            if(employeePanNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase()))
            {
                blException.addException("panNumber","aadharCardNumber"+panNumber+" Exists.");
            }
        }
        if(aadharCardNumber!=null && aadharCardNumber.length()>0)
        {
            if(employeeAadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase()))
            {
                blException.addException("aadharCardNumber","aadharCardNumber"+aadharCardNumber+" Exists.");
            }
        }
        if(blException.hasExceptions())
        {
            throw blException;
        }
        try{            
        EmployeeDAOInterface employeeDAO;
        employeeDAO=new EmployeeDAO();
        EmployeeDTOInterface dsEmployee;
        dsEmployee=new EmployeeDTO();
        dsEmployee.setName(name);
        dsEmployee.setDesignationCode(designation.getCode());
        dsEmployee.setDateOfBirth(dateOfBirth);
        dsEmployee.setGender(gender);
        dsEmployee.setIsIndian(isIndian);
        dsEmployee.setBasicSalary(basicSalary);
        dsEmployee.setPANNumber(panNumber);
        dsEmployee.setAadharCardNumber(aadharCardNumber);
        employeeDAO.add(dsEmployee);
        employee.setEmployeeId(dsEmployee.getEmployeeId());
        EmployeeInterface dlEmployee=new Employee();
        dlEmployee.setEmployeeId(employee.getEmployeeId());
        dlEmployee.setName(employee.getName());
        dlEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
        dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
        dlEmployee.setGender(gender);
        dlEmployee.setIsIndian(isIndian);
        dlEmployee.setBasicSalary(basicSalary);
        dlEmployee.setPANNumber(panNumber);
        dlEmployee.setAadharCardNumber(aadharCardNumber);
        employeesSet.add(dlEmployee);
        this.employeeIdWiseEmployeesMap.put(dlEmployee.getEmployeeId().toUpperCase(),dlEmployee);
        this.employeePanNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dlEmployee);
        this.employeeAadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dlEmployee);
        /* Now Adding Data in new Designation */
        Set<EmployeeInterface> ets;
        ets=this.designationCodeWiseEmployeeMap.get(dlEmployee.getDesignation().getCode());
        if(ets==null)
        {
            ets=new TreeSet<>();
            ets.add(dlEmployee);
            designationCodeWiseEmployeeMap.put(new Integer(dlEmployee.getDesignation().getCode()),ets);
        }
        else
        {
            ets.add(dlEmployee);
        }
        }catch(DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }
    }
    public void updateEmployee(EmployeeInterface employee) throws BLException
    {
        BLException blException;
        blException=new BLException();
        String employeeId=employee.getEmployeeId();
        String name=employee.getName();
        DesignationInterface designation=employee.getDesignation();
        int designationCode=0;
        Date dateOfBirth=employee.getDateOfBirth();
        char gender=employee.getGender();
        boolean isIndian=employee.getIsIndian();
        BigDecimal basicSalary=employee.getBasicSalary();
        String panNumber=employee.getPANNumber();
        String aadharCardNumber=employee.getAadharCardNumber();
        if(employeeId==null)
        {
            blException.addException("employeeId","employee Id required");
        }
        else
        {
            employeeId=employeeId.trim();
            if(employeeId.length()==0)
            {
                blException.addException("employeeId","employeeId is required");
            }
            else
            {
                if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
                {
                    blException.addException("employeeId","Invalid EmployeId :"+employeeId);
                    throw blException;
                }
            }
        }
        if(name==null)
        {
            blException.addException("name","name is null");
        }
        else
        {
            name=name.trim();
            if(name.length()==0)
            {
                blException.addException("name","name length is zero");
            }
        }
        DesignationManagerInterface designationManager;
        designationManager=DesignationManager.getDesignationManager();
        if(designation==null)
        {
            blException.addException("designation","designation is null");
        }
        else
        {
            designationCode=designation.getCode();
            if(designationManager.designationCodeExists(designation.getCode())==false)
            {
                blException.addException("designation","Invalid designation Code");
            }
        }
        if(dateOfBirth==null)
        {
            blException.addException("dateOfBirth","Date Of Birth is null");
        }
        if(gender==' ')
        {
            blException.addException("gender","gender is null");
        }
        if(basicSalary==null)
        {
            blException.addException("basicSalary","Basic Salary is null");
        }
        else
        {
            if(basicSalary.signum()==-1)
            {
                blException.addException("basicSalary","Basic Salary should not be negative");
            }
        }
        if(panNumber==null)
        {
            blException.addException("panNumber","Pan Number is required");
        }
        else
        {
            panNumber=panNumber.trim();
            if(panNumber.length()==0)
            {
                blException.addException("panNumber","pan number length is zero");
            }
        }
        if(aadharCardNumber==null)
        {
            blException.addException("aadharCardNumber","aadhar card number required");
        }
        else
        {
            aadharCardNumber=aadharCardNumber.trim();
            if(aadharCardNumber.length()==0)
            {
                blException.addException("aadharCardNumber","aadhar card number length is zero");
            }
        }
        if(panNumber!=null && panNumber.length()>0)
        {
            EmployeeInterface ee=employeePanNumberWiseEmployeesMap.get(panNumber.toUpperCase());
            if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
            {
                blException.addException("panNumber","panNumber Exists.");
            }
        }
        if(aadharCardNumber!=null && aadharCardNumber.length()>0)
        {
            EmployeeInterface ee=employeeAadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
            if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
            {
                blException.addException("aadharCardNumber","aadhar card number Exists.");
            }
        }
        if(blException.hasExceptions())
        {
            throw blException;
        }
        try
        {
            EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
            String oldPanNumber=dsEmployee.getPANNumber();
            String oldAadharCardNumber=dsEmployee.getAadharCardNumber();
            int oldDesignationCode=dsEmployee.getDesignation().getCode();
            EmployeeDAOInterface employeeDAO;
            employeeDAO=new EmployeeDAO();
            EmployeeDTOInterface dlEmployee;
            dlEmployee=new EmployeeDTO();
            dlEmployee.setEmployeeId(dsEmployee.getEmployeeId());
            dlEmployee.setName(name);
            dlEmployee.setDesignationCode(designation.getCode());
            dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
            dlEmployee.setGender(gender);
            dlEmployee.setIsIndian(isIndian);
            dlEmployee.setBasicSalary(basicSalary);
            dlEmployee.setPANNumber(panNumber);
            dlEmployee.setAadharCardNumber(aadharCardNumber);
            employeeDAO.update(dlEmployee);
            dsEmployee.setName(employee.getName());
            dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
            dsEmployee.setDateOfBirth(dateOfBirth);
            dsEmployee.setGender(gender);
            dsEmployee.setIsIndian(isIndian);
            dsEmployee.setBasicSalary(basicSalary);
            dsEmployee.setPANNumber(panNumber);
            dsEmployee.setAadharCardNumber(aadharCardNumber);
            employeesSet.remove(dsEmployee);
            employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
            employeePanNumberWiseEmployeesMap.remove(oldPanNumber.toUpperCase());
            employeeAadharCardNumberWiseEmployeesMap.remove(aadharCardNumber.toUpperCase());
            employeesSet.add(dsEmployee);
            employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
            employeePanNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
            employeeAadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
            /* Before Adding Data in Designation First We have to remove Old Designation*/
            if(oldDesignationCode!=dsEmployee.getDesignation().getCode())
            {
                Set<EmployeeInterface> ets;
                ets=this.designationCodeWiseEmployeeMap.get(oldDesignationCode);
                ets.remove(dsEmployee);
                ets=this.designationCodeWiseEmployeeMap.get(dsEmployee.getDesignation().getCode());
                if(ets==null)
                {
                    ets=new TreeSet<>();
                    ets.add(dsEmployee);
                    designationCodeWiseEmployeeMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
                }
                else
                {
                    ets.add(dsEmployee);
                }
            }
        }catch(DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }
    }
    public void removeEmployee(String employeeId) throws BLException
    {
        BLException blException;
        blException=new BLException();
                if(employeeId==null)
        {
            blException.addException("employeeId","employee Id required");
        }
        else
        {
            employeeId=employeeId.trim();
            if(employeeId.length()==0)
            {
                blException.addException("employeeId","employeeId is required");
            }
            else
            {
                if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
                {
                    blException.addException("employeeId","Invalid EmployeId :"+employeeId);
                    throw blException;
                }
            }
        }
        try
        {
            EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
            EmployeeDAOInterface employeeDAO;
            employeeDAO=new EmployeeDAO();
            employeeDAO.delete(dsEmployee.getEmployeeId());
            employeesSet.remove(dsEmployee);
            employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
            employeePanNumberWiseEmployeesMap.remove(dsEmployee.getPANNumber().toUpperCase());
            employeeAadharCardNumberWiseEmployeesMap.remove(dsEmployee.getAadharCardNumber().toUpperCase());
            Set<EmployeeInterface> ets;
            ets=this.designationCodeWiseEmployeeMap.get(dsEmployee.getDesignation().getCode());
            ets.remove(dsEmployee);
        }catch(DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }
    }
    public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
    {
        EmployeeInterface employee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
        BLException blException;
        blException=new BLException();
        if(employee==null)
        {
            blException.addException("employeeId","Invalid EmployeeId");
        }
        if(employeeId!=null)
        {
            employeeId=employeeId.trim();
            blException.addException("employeeId","employee id is null");
        }
        else
        {
            if(employeeId.length()==0)
            {
                blException.addException("employeeId","employeeId Length is zero");
            }
        }
        EmployeeInterface d=new Employee();
        d.setEmployeeId(employee.getEmployeeId());
        d.setName(employee.getName());
        DesignationInterface dsDesignation=employee.getDesignation();
        DesignationInterface designation=new Designation();
        designation.setCode(designation.getCode());
        designation.setTitle(designation.getTitle());
        d.setDesignation(designation);
        d.setDateOfBirth((Date)employee.getDateOfBirth().clone());
        d.setGender(employee.getGender());
        d.setIsIndian(employee.getIsIndian());
        d.setBasicSalary(employee.getBasicSalary());
        d.setPANNumber(employee.getPANNumber());
        d.setAadharCardNumber(employee.getAadharCardNumber());
        return d;
    }
    public EmployeeInterface getEmployeeByPanNumber(String panNumber) throws BLException
    {
        EmployeeInterface employee=employeePanNumberWiseEmployeesMap.get(panNumber.toUpperCase());
        if(employee==null)
        {
            BLException blException;
            blException=new BLException();
            blException.addException("panNumber","Invaild Pan-Number"+panNumber);
        }
        EmployeeInterface d=new Employee();
        d.setEmployeeId(employee.getEmployeeId());
        d.setName(employee.getName());
        DesignationInterface dsDesignation=employee.getDesignation();
        DesignationInterface designation=new Designation();
        designation.setCode(designation.getCode());
        designation.setTitle(designation.getTitle());
        d.setDesignation(designation);
        d.setDateOfBirth((Date)employee.getDateOfBirth().clone());
        d.setGender(employee.getGender());
        d.setIsIndian(employee.getIsIndian());
        d.setBasicSalary(employee.getBasicSalary());
        d.setPANNumber(employee.getPANNumber());
        d.setAadharCardNumber(employee.getAadharCardNumber());
        return d;
    }
    public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
    {
        EmployeeInterface employee=employeeAadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
        if(employee==null)
        {
            BLException blException;
            blException=new BLException();
            blException.addException("aadharCardNumber","Invalid Aadhar Card Number");
        }
        EmployeeInterface d=new Employee();
        d.setEmployeeId(employee.getEmployeeId());
        d.setName(employee.getName());
        DesignationInterface dsDesignation=employee.getDesignation();
        DesignationInterface designation=new Designation();
        designation.setCode(designation.getCode());
        designation.setTitle(designation.getTitle());
        d.setDesignation(designation);
        d.setDateOfBirth((Date)employee.getDateOfBirth().clone());
        d.setGender(employee.getGender());
        d.setIsIndian(employee.getIsIndian());
        d.setBasicSalary(employee.getBasicSalary());
        d.setPANNumber(employee.getPANNumber());
        d.setAadharCardNumber(employee.getAadharCardNumber());
        return d;
    }
    public int getEmployeeCount() throws BLException
    {
        return employeesSet.size();
    }
    public boolean employeeIdExists(String employeeId) throws BLException
    {
        return employeeIdWiseEmployeesMap.containsKey(employeeId);
    }
    public boolean employeePanNumberExists(String panNumber) throws BLException
    {
        return employeePanNumberWiseEmployeesMap.containsKey(panNumber);
    }
    public boolean employeeAadharCardNumberExists(String aadharCardNumber) throws BLException
    {
        return employeeAadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber);
    }
    public Set<EmployeeInterface> getEmployees()
    {
        Set<EmployeeInterface> employees;
        //DesignationInterface designation=employee.getDesignation();
        employees=new TreeSet<>();
        employeesSet.forEach((employee)->{
        DesignationInterface dsDesignation=employee.getDesignation();
            DesignationInterface designation=new Designation();
            EmployeeInterface d=new Employee();
            d.setEmployeeId(employee.getEmployeeId());
            d.setName(employee.getName());
            designation.setCode(designation.getCode());
            designation.setTitle(designation.getTitle());
            d.setDesignation(designation);
            d.setDesignation(employee.getDesignation());
            d.setDateOfBirth((Date)employee.getDateOfBirth().clone());
            d.setGender(employee.getGender());
            d.setIsIndian(employee.getIsIndian());
            d.setBasicSalary(employee.getBasicSalary());
            d.setPANNumber(employee.getPANNumber());
            d.setAadharCardNumber(employee.getAadharCardNumber());
            employees.add(d);
        });
        return employees;
    }
    public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode) throws BLException
    {
        DesignationManagerInterface designationManager;
        designationManager=DesignationManager.getDesignationManager();
        if(designationManager.designationCodeExists(designationCode)==false)
        {
            BLException blException;
            blException=new BLException();
            blException.setGenericException("Invalid designation"+designationCode);
            throw blException;
        }
        Set<EmployeeInterface> employees=new TreeSet<>();
        Set<EmployeeInterface> ets;
        ets=designationCodeWiseEmployeeMap.get(designationCode);
        if(ets==null)
        {
            return employees;
        }
        EmployeeInterface employee;
        DesignationInterface dsDesignation;
        DesignationInterface designation;
        for(EmployeeInterface dsEmployee:ets)
        {
            employee=new Employee();
            designation=new Designation();
            dsDesignation=dsEmployee.getDesignation();
            employee.setEmployeeId(dsEmployee.getEmployeeId());
            employee.setName(employee.getName());
            designation.setCode(designation.getCode());
            designation.setTitle(designation.getTitle());
            employee.setDesignation(designation);
            employee.setDesignation(employee.getDesignation());
            employee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
            employee.setGender(employee.getGender());
            employee.setIsIndian(employee.getIsIndian());
            employee.setBasicSalary(employee.getBasicSalary());
            employee.setPANNumber(employee.getPANNumber());
            employee.setAadharCardNumber(employee.getAadharCardNumber());
            employees.add(employee);  
        }
        return employees;
    }
    public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
    {
        Set<EmployeeInterface> ets;
        ets=this.designationCodeWiseEmployeeMap.get(designationCode);
        if(ets==null) return 0;
        return ets.size();
    }
    public boolean designationAlloted(int designationCode) throws BLException
    {
        return this.designationCodeWiseEmployeeMap.containsKey(designationCode);
    }
}