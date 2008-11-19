package com.naildrivin5.fwf.core;

import java.text.*;
import java.util.*;

import com.naildrivin5.fwf.*;

import org.apache.log4j.*;

/** Handles locating the view based on the controller and action name.
*/
public class ViewFinder
{
    private Logger itsLogger = Logger.getLogger(getClass().getName());

    /** Finds the path to the appropriate view based on the controller name and the action provided.
     * @param controllerName the name of the controller (not fully qualified)
     * @param actionName the name of the action
     * @return a path to the JSP.
     */
    public String find(String controllerName, String actionName)
    {
        return "view/" + controllerName + "/" + actionName + ".jsp";
    }
}
