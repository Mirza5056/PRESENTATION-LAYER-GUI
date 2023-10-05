import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.bl.managers.*;
import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import java.util.*;
public class EmployeeManagerAadharCardNumberExistsTestCase
{
    public static void main(String gg[])
    {
        String aadharCardNumber=gg[0];
        try
        {
            EmployeeManagerInterface employeeManager;
            employeeManager=EmployeeManager.getEmployeeManager();
            System.out.println(aadharCardNumber+" Exists. "+employeeManager.employeeAadharCardNumberExists(aadharCardNumber));
        }catch(BLException blException)
        {
            List<String> properties=blException.getProperties();
            properties.forEach((property)->{
                System.out.println(blException.getException(property));
            });
        }
    }
}