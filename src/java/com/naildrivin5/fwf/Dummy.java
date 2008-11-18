package com.naildrivin5.fwf;

public class Dummy
{
    private String itsD;

    public Dummy(String d)
    {
        itsD = d;
    }

    public String getD() { return itsD; }
    public void setD(String i) { itsD = i; }


    public boolean equals(Object o)
    {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!o.getClass().getName().equals(getClass().getName()))
            return false;

        if (itsD == null)
            return false;

        return itsD.equals(((Dummy)o).itsD);
    }
}
