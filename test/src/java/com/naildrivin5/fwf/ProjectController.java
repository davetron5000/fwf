package com.naildrivin5.fwf;

public class ProjectController extends ApplicationController
{
    private String itsFoo;
    public String getFoo() { return itsFoo; }
    public void setFoo(String i) { itsFoo = i; }

    private Long itsAge;
    public Long getAge() { return itsAge; }
    public void setAge(Long i) { itsAge = i; }
}
