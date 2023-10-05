import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.bl.managers.*;
import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import java.util.*;
public class DesignationManagerTitleExistsTestCase
{
    public static void main(String gg[])
    {
        String title=gg[0];
        try{
            DesignationManagerInterface designationManager;
            designationManager=DesignationManager.getDesignationManager();
            System.out.println(title+" Exists."+designationManager.designationTitleExists(title));
        }catch(BLException blException)
        {
            List<String> properties=blException.getProperties();
            properties.forEach((property)->{
                System.out.println(blException.getException(property));
            });
        }
    }
}