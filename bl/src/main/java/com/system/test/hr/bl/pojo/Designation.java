package com.system.test.hr.bl.pojo;
import com.system.test.hr.bl.interfaces.pojo.*;
public class Designation implements DesignationInterface
{
    private int code;
    private String title;
    public Designation()
    {
        this.code=0;
        this.title=null;
    }
        public void setCode(int code)
        {
            this.code=code;
        }
        public int getCode()
        {
            return this.code;
        }
        public void setTitle(String title)
        {
            this.title=title;
        }
        public String getTitle()
        {
            return this.title;
        }
    public boolean equals(Object object)
    {
        if(!(object instanceof DesignationInterface)) return false;
        DesignationInterface designation=(DesignationInterface) object;
        return this.code==designation.getCode(); 
    }
    public int compareTo(DesignationInterface designation)
    {
        return this.code-designation.getCode();
    }
    public int hashCode()
    {
        return code;
    }
}