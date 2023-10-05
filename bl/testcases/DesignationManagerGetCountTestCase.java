import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import com.system.test.hr.bl.managers.*;
import java.util.*;
public class DesignationManagerGetCountTestCase
{
    public static void main(String gg[])
    {
        try{
            DesignationManagerInterface designationManager;
            designationManager=DesignationManager.getDesignationManager();
            System.out.println("Designation Count  :"+designationManager.getDesignationCount());
        }catch(BLException blException)
        {
            List<String> properties=blException.getProperties();
            properties.forEach((property)->{
                System.out.println(blException.getException(property));
            });
        }
    }
}