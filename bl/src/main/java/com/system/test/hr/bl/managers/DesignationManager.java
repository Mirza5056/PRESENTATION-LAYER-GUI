package com.system.test.hr.bl.managers;
import com.system.test.hr.bl.interfaces.pojo.*;
import com.system.test.hr.bl.pojo.*;
import com.system.test.hr.bl.interfaces.managers.*;
import com.system.test.hr.bl.exceptions.*;
import com.system.test.hr.dl.exceptions.*;
import com.system.test.hr.dl.interfaces.dto.*;
import com.system.test.hr.dl.interfaces.dao.*;
import com.system.test.hr.dl.dao.*;
import com.system.test.hr.dl.dto.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
    private Map<Integer,DesignationInterface> codeWiseDesignationMap;
    private Map<String,DesignationInterface> titleWiseDesignationMap;
    private Set<DesignationInterface> designationsSet;
    private static DesignationManager designationManager=null;
    private DesignationManager() throws BLException
    {
        populateDataStructures();
    }
    private void populateDataStructures() throws BLException
    {
        this.codeWiseDesignationMap=new HashMap<>();
        this.titleWiseDesignationMap=new HashMap<>();
        this.designationsSet=new TreeSet<>();
        try{
            Set<DesignationDTOInterface> dlDesignations;
            dlDesignations=new DesignationDAO().getAll();
            DesignationInterface designation;
            for(DesignationDTOInterface dlDesignation:dlDesignations)
            {
                designation=new Designation();
                designation.setCode(dlDesignation.getCode());
                designation.setTitle(dlDesignation.getTitle());
                this.codeWiseDesignationMap.put(new Integer(designation.getCode()),designation);
                this.titleWiseDesignationMap.put(designation.getTitle().toUpperCase(),designation);
                this.designationsSet.add(designation);
            }
        }catch(DAOException daoException)
        {
            BLException blException=new BLException();
            blException.setGenericException(daoException.getMessage());
            throw blException;
        }
    }
    public static DesignationManagerInterface getDesignationManager() throws BLException
    {
        if(designationManager==null) designationManager=new DesignationManager();
        return designationManager;
    }
    public void addDesignation(DesignationInterface designation) throws BLException
    {
        BLException blException;
        blException=new BLException();
        if(designation==null)
        {
            blException.setGenericException("Title should be required");
            throw blException;
        }
        int code=designation.getCode();
        String title=designation.getTitle();
        if(code!=0)
        {
            blException.addException("code","code should not be zero");
        }
        if(title==null)
        {
            blException.addException("title","title required");
            title="";
        }
        else
        {
            title=title.trim();
            if(title.length()==0)
            {
                blException.addException("title","title should not be zero");
            }
        }
        if(title.length()>0)
        {
            if(this.titleWiseDesignationMap.containsKey(title.toUpperCase()))
            {
                blException.addException("title","Designation :"+title+" Exists.");
            }
        }
        if(blException.hasExceptions())
        {
            throw blException;
        }
        try{
            DesignationDTOInterface designationDTO;
            designationDTO=new DesignationDTO();
            designationDTO.setTitle(title);
            DesignationDAOInterface designationDAO;
            designationDAO=new DesignationDAO();
            designationDAO.add(designationDTO);
            code=designationDTO.getCode();
            designation.setCode(code);
            Designation dsDesignation;
            dsDesignation=new Designation();
            dsDesignation.setCode(code);
            dsDesignation.setTitle(title);
            this.codeWiseDesignationMap.put(new Integer(code),dsDesignation);
            this.titleWiseDesignationMap.put(title.toUpperCase(),dsDesignation);
            designationsSet.add(dsDesignation);
        }catch(DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }
    }
    public void updateDesignation(DesignationInterface designation) throws BLException
    {
        BLException blException;
        blException=new BLException();
        if(designation==null)
        {
            blException.setGenericException("Designation should not be null");
            throw blException;
        }
        int code=designation.getCode();
        String title=designation.getTitle();
        if(code<=0)
        {
            blException.addException("code","Invalid code :"+code);
        }
        if(code>0)
        {
            if(codeWiseDesignationMap.containsKey(new Integer(code))==false)
            {
                blException.addException("code","Code exists.");
            }
        }
        if(title==null)
        {
            blException.addException("title","title is null");
            title="";
        }
        else
        {
            title=title.trim();
            if(title.length()==0)
            {
                blException.addException("title","title length should not be zero");
            }
        }
        if(title.length()>0)
        {
            DesignationInterface d;
            d=titleWiseDesignationMap.get(title.toUpperCase());
        if(d!=null && d.getCode()!=code)
        {
            blException.addException("title","Designation :"+title+" Exists.");
        }
        }
        if(blException.hasExceptions())
        {
            throw blException;
        }
        try{
            DesignationInterface dsDesignation=codeWiseDesignationMap.get(code);
            DesignationDTOInterface dlDesignation=new DesignationDTO();
            dlDesignation.setCode(code);
            dlDesignation.setTitle(title);
            DesignationDAOInterface designationDAO;
            designationDAO=new DesignationDAO();
            designationDAO.update(dlDesignation);
            codeWiseDesignationMap.remove(code);
            titleWiseDesignationMap.remove(dsDesignation.getTitle().toUpperCase());
            designationsSet.remove(dsDesignation);
            dsDesignation.setTitle(title);
            codeWiseDesignationMap.put(code,dsDesignation);
            titleWiseDesignationMap.put(title.toUpperCase(),dsDesignation);
            designationsSet.add(dsDesignation);
        }catch(DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }
    }
    public void removeDesignation(int code) throws BLException
    {    
        BLException blException;
        blException=new BLException();
        if(code<=0)
        {
            blException.addException("code","Invalid Code :"+code);
            throw blException;
        }
        if(code>0)
        {
            if(this.codeWiseDesignationMap.containsKey(new Integer(code))==false)
            {
                blException.addException("code","Invalid code :"+code);
                throw blException;
            }
        }  
        try{
            DesignationInterface dsDesignation=codeWiseDesignationMap.get(code);
            DesignationDAOInterface designationDAO;
            designationDAO=new DesignationDAO();
            designationDAO.delete(code);
            codeWiseDesignationMap.remove(code);
            titleWiseDesignationMap.remove(dsDesignation.getTitle().toUpperCase());
            designationsSet.remove(dsDesignation);
        }catch(DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
            throw blException;
        }
    }
    public DesignationInterface getDesignationByCode(int code) throws BLException
    {
        DesignationInterface designation;
        designation=codeWiseDesignationMap.get(code);
        if(designation==null)
        {
            BLException blException;
            blException=new BLException();
            blException.addException("code","Invalid code :"+code);
        }
        DesignationInterface d=new Designation();
        d.setCode(designation.getCode());
        d.setTitle(designation.getTitle());
        return designation;
    }
    public DesignationInterface getDesignationByTitle(String title) throws BLException
    {
        DesignationInterface designation=titleWiseDesignationMap.get(title.toUpperCase());
        if(designation==null)
        {
            BLException blException;
            blException=new BLException();
            blException.addException("title","Invalid Title");
        }
        DesignationInterface d=new Designation();
        d.setCode(designation.getCode());
        d.setTitle(designation.getTitle());
        return designation;
    }
    DesignationInterface getDSDesignationByCode(int code) 
    {
        DesignationInterface designation;
        designation=codeWiseDesignationMap.get(code);
        return designation;
    }
    public int getDesignationCount()
    {
        return designationsSet.size();
    }
    public boolean designationCodeExists(int code) 
    {
        return codeWiseDesignationMap.containsKey(code);
    }
    public boolean designationTitleExists(String title) 
    {
        return titleWiseDesignationMap.containsKey(title.toUpperCase());
    }
    public Set<DesignationInterface> getDesignations() 
    {
        Set<DesignationInterface> designations;
        designations=new TreeSet<>();
        designationsSet.forEach((designation)->{
            DesignationInterface d=new Designation();
            d.setCode(designation.getCode());
            d.setTitle(designation.getTitle());
            designations.add(d);
        });
        return designations;
    }
}