import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.bl.managers.*;
import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import java.util.*;
public class DesignationManagerUpdateTestCase
{
    public static void main(String gg[])
    {
        int code=Integer.parseInt(gg[0]);
        String title=gg[1];
        DesignationInterface designation=new Designation();
        designation.setCode(code);
        designation.setTitle(title);
        try{
            DesignationManagerInterface designationManager;
            designationManager=DesignationManager.getDesignationManager();
            designationManager.updateDesignation(designation);
            System.out.println("Update Successfully\n");
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