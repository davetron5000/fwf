package com.naildrivin5.fwf;

/** Base class for all controllers in the system.
 */
public class ApplicationController
{
    private Long itsId;
    private Parameters itsParams;

    protected Long getId() 
    { 
        return itsId; 
    }

    void setId(Long i) 
    { 
        itsId = i; 
    }

    /** Provides access to the parameters of the request.
     * @return a Parameters with the params of the request.
     */
    protected Parameters params()
    {
        return itsParams;
    }

    void setParams(Parameters params)
    {
        itsParams = params;
    }

}
