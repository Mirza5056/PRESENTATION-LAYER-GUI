import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.bl.managers.*;
import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import java.util.*;
public class DesignationManagerGetAllTestCase
{
    public static void main(String gg[])
    {
        try{
            DesignationManagerInterface designationManager;
            designationManager=DesignationManager.getDesignationManager();
            Set<DesignationInterface> designations;
            designations=designationManager.getDesignations();
            designations.forEach((designation)->{
                System.out.printf("Code :%d,  Title :%s\n",designation.getCode(),designation.getTitle());
            });
        }catch(BLException blException)
        {
            List<String> properties=blException.getProperties();
            properties.forEach((property)->{
                System.out.println(blException.getException(property));
            });
        }
    }
}