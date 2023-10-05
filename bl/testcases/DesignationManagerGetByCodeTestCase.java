import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.bl.managers.*;
import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import java.util.*;
public class DesignationManagerGetByCodeTestCase
{
    public static void main(String gg[])
    {
        int code=Integer.parseInt(gg[0]);
        try{
            DesignationManagerInterface designationManager;
            designationManager=DesignationManager.getDesignationManager();
            DesignationInterface designation;
            designation=designationManager.getDesignationByCode(code);
            System.out.printf("Code :%d, Title :%s\n",designation.getCode(),designation.getTitle());
        }catch(BLException blException)
        {
            List<String> properties=blException.getProperties();
            properties.forEach((property)->{
                System.out.println(blException.getException(property));
            });
        }
    }
}