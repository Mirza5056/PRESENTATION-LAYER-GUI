package com.system.test.hr.pl.ui;
import com.system.test.hr.pl.model.*;
import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.pojo.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;
import javax.swing.filechooser.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
    private JLabel titleLabel;
    private JLabel searchLabel;
    private JLabel searchErrorLabel;
    private JTextField searchTextField;
    private JButton clearSearchFieldButton;
    private JTable designationTable;
    private DesignationModel designationModel;
    private JScrollPane scrollPane;
    private Container container;
    /* **************************************** */
    private DesignationPanel designationPanel;
    private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
    private MODE mode;
    /* *************************************** */
    private ImageIcon logoIcon;
    private ImageIcon addIcon;
    private ImageIcon editIcon;
    private ImageIcon updateIcon;
    private ImageIcon deleteIcon;
    private ImageIcon removeIcon;
    private ImageIcon saveIcon;
    private ImageIcon exportToPDFIcon;
    private ImageIcon cancelIcon;
    /* ****************************************** */
    private ImageIcon clearIcon;
    public DesignationUI()
    {
        initComponents();
        addAppreance();
        addListeners();
        setViewMode();
        designationPanel.setViewMode();
    }
    private void initComponents() // Object Creation in Init Component
    {        
        logoIcon=new ImageIcon(this.getClass().getResource("/icons/logo.png"));
        setIconImage(logoIcon.getImage());
        /* ****************************************** */       
        addIcon=new ImageIcon(this.getClass().getResource("/icons/add_icon.png"));
        saveIcon=new ImageIcon(this.getClass().getResource("/icons/save_icon.png"));
        editIcon=new ImageIcon(this.getClass().getResource("/icons/edit_icon.png"));
        deleteIcon=new ImageIcon(this.getClass().getResource("/icons/delete_icon.png"));
        updateIcon=new ImageIcon(this.getClass().getResource("/icons/update_icon.png"));
        exportToPDFIcon=new ImageIcon(this.getClass().getResource("/icons/pdf_icon.png"));
        cancelIcon=new ImageIcon(this.getClass().getResource("/icons/cancel_icon.png"));
        /* ****************************************** */
        clearIcon=new ImageIcon(this.getClass().getResource("/icons/cancel1_icon.png"));
        designationModel=new DesignationModel();
        titleLabel=new JLabel("Designation");
        searchLabel=new JLabel("Search");
        searchTextField=new JTextField();
        clearSearchFieldButton=new JButton(clearIcon);
        searchErrorLabel=new JLabel("");
        designationTable=new JTable(designationModel);
        scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container=getContentPane();
    }    
    private void addAppreance() // Font Setting add Layout all Come in setAppereance
    {
        Font titleFont=new Font("Verdana",Font.BOLD,18);
        titleLabel.setFont(titleFont);
        Font captionFont=new Font("Corbal",Font.BOLD,18);
        searchLabel.setFont(captionFont);
        Font dataFont=new Font("Verdana",Font.PLAIN,15);     
        searchTextField.setFont(dataFont);
        Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
        searchErrorLabel.setFont(searchErrorFont);
        Font headerFont=new Font("Verdana",Font.BOLD,13);
        searchErrorLabel.setForeground(Color.red);
        designationPanel=new DesignationPanel();
        container.setLayout(null);
        //header.sereasizing allowed false 
        //tableHeader.setResizingAllowed(false);
        designationTable.getTableHeader().setResizingAllowed(false);
        designationTable.getTableHeader().setReorderingAllowed(false);
        designationTable.setRowSelectionAllowed(true);
        //designationTable.setSelectionModel(ListSelectionModel.SINGLE_SELECTION);
        designationTable.setRowHeight(30);
        designationTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        designationTable.getColumnModel().getColumn(1).setPreferredWidth(380);
        // Table Header Font set
        JTableHeader tableHeader=designationTable.getTableHeader();
        tableHeader.setFont(headerFont);
        int lm; // Left Margin
        int tm; // Top Margin
        lm=0;
        tm=0;
        titleLabel.setBounds(lm+10,tm+10,200,50); // 200 Width 50 height
        searchLabel.setBounds(lm+10,tm+10+43+20,100,30);
        searchTextField.setBounds(lm+10+100+5,tm+10+60+5,300,30);
        clearSearchFieldButton.setBounds(lm+10+400+10,tm+10+60+5,30,30);
        searchErrorLabel.setBounds(lm+10+100+295+10-70,tm+10+23+20,100,20);
        scrollPane.setBounds(lm+10, tm+10+85+10+20, 470,300);
        designationPanel.setBounds(lm+10,tm+10+400+20,465,170);

        container.add(titleLabel);
        container.add(searchLabel);
        container.add(searchTextField);
        container.add(clearSearchFieldButton);
        container.add(searchErrorLabel);
        container.add(scrollPane);
        container.add(designationPanel);
        int w,h;
        w=500;
        h=650;
        setSize(w,h);
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
    }
    private void addListeners()
    {
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent ev)
            {
                System.exit(0);
            }
        });
        searchTextField.getDocument().addDocumentListener(this);
        clearSearchFieldButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev)
            {
                clearSearchFieldButton.setText("");
                clearSearchFieldButton.requestFocus();
            }
        });
        designationTable.getSelectionModel().addListSelectionListener(this);
    }
    private void searchDesignation()
    {
        searchErrorLabel.setText("");
        String title=searchTextField.getText().trim();
        if(title.length()==0) return;
        int rowIndex;
        try{
            rowIndex=designationModel.indexOfTitle(title,true);
        }catch(BLException blException)
        {
            searchErrorLabel.setText("Not Found");
            return;
        }
        designationTable.setRowSelectionInterval(rowIndex,rowIndex);
        Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
        designationTable.scrollRectToVisible(rectangle);
    }
    public void changedUpdate(DocumentEvent dv)
    {
        searchDesignation();
    }
    public void insertUpdate(DocumentEvent dv)
    {
        searchDesignation();
    }
    public void removeUpdate(DocumentEvent dv)
    {
        searchDesignation();
    }
    public void valueChanged(ListSelectionEvent ev)
    {
        int selectedRowIndex=designationTable.getSelectedRow();
        try{
            DesignationInterface designation=designationModel.getDesignationAt(selectedRowIndex);
            designationPanel.setDesignation(designation);
        }catch(BLException blException)
        {
            designationPanel.clearDesignation();
        }
    }
    private void setViewMode()
    {
        this.mode=MODE.VIEW;
        if(designationModel.getRowCount()==0)
        {
            searchTextField.setEnabled(false);
            clearSearchFieldButton.setEnabled(false);
            designationTable.setEnabled(false);
        }
        else
        {
            searchTextField.setEnabled(true);
            clearSearchFieldButton.setEnabled(true);
            designationTable.setEnabled(true);
        }
    }
    public void setAddMode()
    {
        this.mode=MODE.ADD;
        searchTextField.setEnabled(false);
        clearSearchFieldButton.setEnabled(false);
        designationTable.setEnabled(false);
    }
    public void setEditMode()
    {
        this.mode=MODE.EDIT;
        searchTextField.setEnabled(false);
        clearSearchFieldButton.setEnabled(false);
        designationTable.setEnabled(false);
    }
    public void setDeleteMode()
    {
        this.mode=MODE.DELETE;
        searchTextField.setEnabled(false);
        clearSearchFieldButton.setEnabled(false);
        designationTable.setEnabled(false);
    }
    public void setExportToPDFMode()
    {
        this.mode=MODE.EXPORT_TO_PDF;
        searchTextField.setEnabled(false);
        clearSearchFieldButton.setEnabled(false);
        designationTable.setEnabled(false);
    }
    // Inner Class Called
    class DesignationPanel extends JPanel 
    {
        private JLabel titleCaptionLabel;
        private JLabel titleLabel;
        private JTextField titleField;
        private JButton clearSearchFieldButton;
        private JButton addButton;
        private JButton editButton;
        private JButton deleteButton;
        private JButton cancelButton;
        private JButton exportToPDFButton;
        private JPanel buttonsPanel;
        private DesignationInterface designation;
        DesignationPanel()
        {
            setBorder(BorderFactory.createLineBorder(new Color(125,125,125)));
            initComponents();
            setAppereance();
            addListeners();
        }
        public void setDesignation(DesignationInterface designation)
        {
            this.designation=designation;
            titleLabel.setText(designation.getTitle());
        }
        public void clearDesignation()
        {
            this.designation=null;
            titleLabel.setText("");
        }
        private void initComponents()
        {
            titleCaptionLabel=new JLabel("Designation");
            titleLabel=new JLabel("");
            titleField=new JTextField();
            buttonsPanel=new JPanel();
            clearSearchFieldButton=new JButton(clearIcon);
            addButton=new JButton(addIcon);
            editButton=new JButton(editIcon);
            cancelButton=new JButton(cancelIcon);
            deleteButton=new JButton(deleteIcon);
            exportToPDFButton=new JButton(exportToPDFIcon);
        }
        private void setAppereance()
        {
            Font captionFont=new Font("Verdana",Font.BOLD,16);
            Font dataFont=new Font("Verdana",Font.PLAIN,16);
            titleCaptionLabel.setFont(captionFont);
            titleLabel.setFont(dataFont);
            titleField.setFont(dataFont);
            setLayout(null);
            int tm,lm;
            tm=0;
            lm=0;
            titleCaptionLabel.setBounds(lm+10,tm+20,110,30);
            titleLabel.setBounds(lm+130,tm+20,150,30);
            titleField.setBounds(120,tm+20,275,30);
            buttonsPanel.setBounds(40,tm+77,370,70);
            addButton.setBounds(35,15,45,45);
            editButton.setBounds(95,15,45,45);
            deleteButton.setBounds(160,15,45,45);
            cancelButton.setBounds(220,15,45,45);
            exportToPDFButton.setBounds(290,15,45,45);
            clearSearchFieldButton.setBounds(lm+400,tm+20,30,30);
            buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
            buttonsPanel.setLayout(null);
            buttonsPanel.add(addButton);
            buttonsPanel.add(editButton);
            buttonsPanel.add(deleteButton);
            buttonsPanel.add(cancelButton);
            buttonsPanel.add(exportToPDFButton);
            add(titleCaptionLabel);
            add(titleLabel);
            add(titleField);
            add(clearSearchFieldButton);
            add(buttonsPanel);
        }
        private boolean addDesignation()
        {
            String title=titleField.getText().trim();
            if(title.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Please Enter Designation");
                titleField.requestFocus();
                return false;
            }
            DesignationInterface d=new Designation();
            d.setTitle(title);
            try
            {
                designationModel.add(d);
                int rowIndex=0;
                try
                {
                    rowIndex=designationModel.indexOfDesignation(d);
                }catch(BLException blException)
                {
                    // do nothing
                }
                designationTable.setRowSelectionInterval(rowIndex,rowIndex);
                Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
                designationTable.scrollRectToVisible(rectangle);
                return true;
            }catch(BLException blException)
            {
                if(blException.hasGenericException())
                {
                    JOptionPane.showMessageDialog(this,blException.getGenericException());
                }
                else
                {
                    if(blException.hasException("title"))
                    {
                        JOptionPane.showMessageDialog(this,blException.getException("title"));
                    }
                }
                titleField.requestFocus();
                return false;
            }
        }
        private boolean updateDesignation()
        {
            String title=titleField.getText().trim();
            if(title.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Enter Designation To Update");
                titleField.requestFocus();
                return false;
            }
            DesignationInterface d=new Designation();
            d.setCode(this.designation.getCode());
            d.setTitle(title);
            try
            {
                designationModel.update(d);
                int rowIndex=0;
                try
                {
                    rowIndex=designationModel.indexOfDesignation(d);
                }catch(BLException blException)
                {
                }
                designationTable.setRowSelectionInterval(rowIndex,rowIndex);
                Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
                designationTable.scrollRectToVisible(rectangle);
                return true;
            }catch(BLException blException)
            {
                if(blException.hasGenericException())
                {
                    JOptionPane.showMessageDialog(this,blException.getGenericException());
                }
                else
                {
                    if(blException.hasException("title"))
                    {
                        JOptionPane.showMessageDialog(this,blException.getException("title"));
                    }
                }
                titleField.requestFocus();
                return false;
            }
        }
        private void removeDesignation()
        {
            try
            {
                String title=this.designation.getTitle();
                int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title+" ?"," Confirmation",JOptionPane.YES_NO_OPTION);
                if(selectedOption==JOptionPane.NO_OPTION) return;
                if(selectedOption==JOptionPane.CANCEL_OPTION) return;
                designationModel.remove(this.designation.getCode());
                JOptionPane.showMessageDialog(this,title+" Deleted");
                this.clearDesignation();
            }catch(BLException blException)
            {
                if(blException.hasGenericException())
                {
                    JOptionPane.showMessageDialog(this,blException.getGenericException());
                }
                else
                {
                    if(blException.hasException("title"))
                    {
                        JOptionPane.showMessageDialog(this,blException.getException("title"));
                    }
                }
            }
        }
        // add Listener Implementation
        private void addListeners()
        {
            // Export To Pdf Button
            this.exportToPDFButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev)
                {
                    JFileChooser jfc=new JFileChooser();
                    jfc.setAcceptAllFileFilterUsed(false);
                    jfc.setCurrentDirectory(new File("."));
                    jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){
                        public boolean accept(File file)
                        {
                            if(file.isDirectory()) return true;
                            if(file.getName().endsWith(".pdf")) return true;
                            return false;
                        }
                        public String getDescription()
                        {
                            return ".pdf";
                        }
                    });
                    int selectedOption=jfc.showSaveDialog(DesignationUI.this);
                    if(selectedOption==jfc.APPROVE_OPTION)
                    {
                        try
                        {
                        File selectedFile=jfc.getSelectedFile();
                        String pdfFile=selectedFile.getAbsolutePath();
                        if(pdfFile.endsWith(".")) pdfFile+="pdf";
                        else if(pdfFile.endsWith(".pdf")==false) pdfFile+=".pdf";
                        File file=new File(pdfFile);
                        //System.out.println(file.getParent());
                        File parent=new File(file.getParent());
                        if(parent.exists()==false || parent.isDirectory()==false)
                        {
                            JOptionPane.showMessageDialog(DesignationUI.this,"Path Does Not Exists\n"+file.getAbsolutePath()+JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        designationModel.exportToPDF(file);
                        JOptionPane.showMessageDialog(DesignationUI.this,"Data export to :\n"+file.getAbsolutePath());
                        }catch(BLException blException)
                        {
                            if(blException.hasGenericException())
                            {
                                JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
                            }
                        }
                        catch(Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                    else
                    {
                        System.out.println("Not saving");
                    }
                }
            });
            // Add Listener Button
            this.addButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev)
                {
                    if(mode==MODE.VIEW)
                    {
                        setAddMode();
                    }
                    else
                    {
                        if(addDesignation())
                        {
                        setViewMode();
                        }
                    }
                }
            });
            // Update Listener Implementation
            this.editButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev)
                {
                    if(mode==MODE.VIEW)
                    {
                        setEditMode();
                    }
                    else
                    {
                        if(updateDesignation())
                        {
                        setViewMode();
                        }
                    }
                }
            });
            // Cancel Listener Implemantation
            this.cancelButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev)
                {
                    setViewMode();
                }
            });
            // Delete Listener Implemantation
            this.deleteButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev)
                {
                    setViewMode();
                    setDeleteMode();
                }
            });
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        void setViewMode()
        {
            DesignationUI.this.setViewMode();
            this.addButton.setIcon(addIcon);
            this.editButton.setText("");
            this.titleField.setVisible(false);
            this.titleLabel.setVisible(true);
            this.addButton.setEnabled(true);
            this.cancelButton.setEnabled(false);
            this.clearSearchFieldButton.setVisible(false);
            if(designationModel.getRowCount()>0)
            {
                this.editButton.setEnabled(true);
                this.deleteButton.setEnabled(true);
                this.exportToPDFButton.setEnabled(true);
            }
            else
            {
                this.editButton.setEnabled(false);
                this.deleteButton.setEnabled(false);
                this.exportToPDFButton.setEnabled(false);
            }
        }
        void setAddMode()
        {
            DesignationUI.this.setAddMode();
            this.titleField.setText("");
            this.clearSearchFieldButton.setVisible(true);
            this.titleLabel.setVisible(false);
            this.titleField.setVisible(true);
            addButton.setIcon(saveIcon);
            this.editButton.setEnabled(false);
            this.cancelButton.setEnabled(true);
            this.deleteButton.setEnabled(false);
            this.exportToPDFButton.setEnabled(false);
        }
        void setEditMode()
        {
            if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
            {
                JOptionPane.showMessageDialog(this,"Please Select Designation To Edit");
                return;
            }
            this.clearSearchFieldButton.setVisible(true);
            DesignationUI.this.setEditMode();
            this.titleField.setText(this.designation.getTitle());
            this.titleLabel.setVisible(false);
            this.titleField.setVisible(true);
            this.addButton.setEnabled(false);
            this.cancelButton.setEnabled(true);
            this.deleteButton.setEnabled(false);
            this.exportToPDFButton.setEnabled(false);
            editButton.setIcon(updateIcon);
        }
        void setDeleteMode()
        {
            if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
            {
                JOptionPane.showMessageDialog(this,"Please Select Designation To Delete");
                return;
            }
            DesignationUI.this.setDeleteMode();
            this.addButton.setEnabled(false);
            this.editButton.setEnabled(false);
            this.cancelButton.setEnabled(false);
            this.deleteButton.setEnabled(false);
            this.exportToPDFButton.setEnabled(false);
            removeDesignation();
            DesignationUI.this.setViewMode();
            setViewMode();
        }
        void setExportToPDFMode()
        {
            DesignationUI.this.setExportToPDFMode();
            this.addButton.setEnabled(false);
            this.editButton.setEnabled(false);
            this.deleteButton.setEnabled(false);
            this.cancelButton.setEnabled(false);
            this.exportToPDFButton.setEnabled(false);
        }   
    } // inner class ends
}