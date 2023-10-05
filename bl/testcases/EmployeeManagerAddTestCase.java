import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.bl.managers.*;
import com.system.test.hr.bl.exceptions.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class EmployeeManagerAddTestCase
{
    public static void main(String gg[])
    {
        try{
                    String name="Mirza";
                    DesignationInterface designation=new Designation();
                    designation.setCode(3);
                    Date dateOfBirth=new Date();
                    char gender='M';
                    boolean isIndian=false;
                    BigDecimal basicSalary=new BigDecimal("254545450.32");
                    String panNumber="DQVA4545";
                    String aadharCardNumber="DQVA4545";
                    /*String name=gg[0];
                    DesignationInterface designation=new Designation();
                    designation.setCode(4);
                    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                    Date dateOfBirth=null;
                    try
                    {
                        dateOfBirth=sdf.parse(gg[2]);
                    }catch(ParseException p)
                    {
                        System.out.println(p.getMessage());
                    }
                    char gender=gg[3].charAt(0);
                    boolean isIndian=Boolean.parseBoolean(gg[4]);
                    BigDecimal basicSalary=new BigDecimal(gg[5]);
                    String panNumber=gg[6];
                    String aadharCardNumber=gg[7];*/
                    EmployeeInterface employee;
                    employee=new Employee();
                    employee.setName(name);
                    employee.setDesignation(designation);
                    employee.setDateOfBirth(dateOfBirth);
                    employee.setGender(gender);
                    employee.setIsIndian(isIndian);
                    employee.setBasicSalary(basicSalary);
                    employee.setPANNumber(panNumber);
                    employee.setAadharCardNumber(aadharCardNumber);
                    EmployeeManagerInterface employeeManager;
                    employeeManager=EmployeeManager.getEmployeeManager();
                    employeeManager.addEmployee(employee);
                    System.out.println("Employee Added With Code As :"+employee.getEmployeeId());
                }catch(BLException blException)
                {
                    List<String>properties=blException.getProperties();
                    properties.forEach((property)->{
                    System.out.println(blException.getException(property));
                });
        }
    }
}
