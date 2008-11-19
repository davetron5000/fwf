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
     * @param modelName the name of the model (not fully qualified)
     * @param actionName the name of the action <b>that was requested</b>
     * @param response the response after the controller's action has fired.
     * @return a path to the JSP.
     */
    public String find(String modelName, String actionName, Response response)
    {
        String model = modelName;
        String view = actionName;

        if (response.getModelName() != null)
            model = response.getModelName();

        if (response.getViewName() != null)
            view = response.getViewName();

        return "view/" + model + "/" + view + ".jsp";
    }
}
