package com.system.test.hr.bl.interfaces.pojo;
public interface DesignationInterface extends Comparable<DesignationInterface>,java.io.Serializable
{
    public void setCode(int code);
    public int getCode();
    public void setTitle(String title);
    public String getTitle();
}