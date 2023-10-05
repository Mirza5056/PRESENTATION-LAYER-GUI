import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.bl.managers.*;
import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import java.util.*;
public class DesignationManagerRemoveTestCase
{
    public static void main(String gg[])
    {
        int code=Integer.parseInt(gg[0]);
        try{
            DesignationManagerInterface designationManager;
            designationManager=DesignationManager.getDesignationManager();
            designationManager.removeDesignation(code);
            System.out.println("Designation Removed Successfully");
        }catch(BLException blException)
        {
            if(blException.hasGenericException())
            {
                System.out.println(blException.getGenericException());
            }
            List<String> properties=blException.getProperties();
            for(String property:properties)
            {
                System.out.println(blException.getException(property));
            }
        }
    }
}