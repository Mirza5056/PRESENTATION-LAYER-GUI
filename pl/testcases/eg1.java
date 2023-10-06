import javax.swing.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class basic extends JFrame implements ActionListener
{
    private JButton b1,b2;
    basic()
    {
        b1=new JButton("1");
        b2=new JButton("2");
    setLayout(new FlowLayout());
    add(b1);
    add(b2);
    b1.setActionCommand("One");
    b2.setActionCommand("Two");
    this.b1.addActionListener(this);
    this.b2.addActionListener(this);
    setLocation(300,300);
    setSize(300,300);
    setVisible(true);
    }
    public void actionPerformed(ActionEvent ev)
    {
        if(ev.getActionCommand().equals("One")) b1.setEnabled(false);
        else b1.setEnabled(true);
        if(ev.getActionCommand().equals("Two")) b2.setEnabled(false);
        else b2.setEnabled(true);
    }
}
class basic1
{
    public static void main(String gg[])
    {
        basic b=new basic();
    }
}