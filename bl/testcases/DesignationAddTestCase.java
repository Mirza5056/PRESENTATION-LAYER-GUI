import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import com.system.test.hr.bl.managers.*;
import java.util.*;
class DesignationAddTestCase
{
    public static void main(String gg[])
    {
        String title=gg[0];
        DesignationInterface designation;
        designation=new Designation();
        designation.setTitle(title);
        try{
            DesignationManagerInterface designationManager;
            designationManager=DesignationManager.getDesignationManager();
            designationManager.addDesignation(designation);
            System.out.println("Designation added with code as :"+designation.getCode());
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