import com.system.test.hr.pl.model.*;
import com.system.test.hr.bl.exceptions.*;
import javax.swing.*;
import java.awt.*;
class DesignationModelTestCase extends JFrame
{
    private JTable tb;
    private JScrollPane jsp;
    private DesignationModel designationModel;
    private Container container;
    DesignationModelTestCase()
    {
        designationModel=new DesignationModel();
        tb=new JTable(designationModel);
        jsp=new JScrollPane(tb,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container=getContentPane();
        container.setLayout(new BorderLayout());
        container.add(jsp);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(500,500);
        setSize(400,400);
        setVisible(true);
    }
    public static void main(String gg[])
    {
        DesignationModelTestCase dmtc=new DesignationModelTestCase();
    }
}