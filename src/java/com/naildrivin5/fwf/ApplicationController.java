package com.naildrivin5.fwf;

import org.apache.log4j.*;

/** Base class for all controllers in the system.  Subclasses can access information from the request via various means:
 * <ul>
 * <li> {@link #params()} - provides access to all parameters as strings (though it would be easier for the controller to provide accessors)</li>
 * <li> {@link #getId()} - provides access to the id provided in the URI</li>
 * <li> {@link #logger()} - provides access to the logger</li>
 * </ul>
 */
public class ApplicationController
{
    private Long itsId;
    private Parameters itsParams;
    private Logger itsLogger = Logger.getLogger(getClass().getName());

    /** Provides access to a shared logger.
     * @return a logger configured for the implementing class (not hard-coded to ApplicationController).
     *
     */
    protected Logger logger()
    {
        return itsLogger;
    }

    protected Long getId() 
    { 
        return itsId; 
    }

    public void setId(Long i) 
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

    public void setParams(Parameters params)
    {
        itsParams = params;
    }

}
