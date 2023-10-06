import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.pl.model.*;
import java.awt.*;
import javax.swing.*;
class EmployeeModelTestCase extends JFrame
{
    private JTable table;
    private EmployeeModel employeeModel;
    private Container container;
    private JScrollPane jsp;
    EmployeeModelTestCase()
    {
        employeeModel=new EmployeeModel();
        table=new JTable(employeeModel);
        jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container=getContentPane();
        container.setLayout(new BorderLayout());
        container.add(jsp);
        setLocation(300,300);
        setSize(300,300);
        setVisible(true);
    }
    public static void main(String gg[])
    {
        EmployeeModelTestCase emtc=new EmployeeModelTestCase();
    }
}