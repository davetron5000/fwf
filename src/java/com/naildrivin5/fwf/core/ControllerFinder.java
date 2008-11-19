package com.naildrivin5.fwf.core;

import java.text.*;
import java.util.*;

import com.naildrivin5.fwf.*;

import org.apache.log4j.*;

/** Handles locating the controller based on a list of packages and a model name.
*/
public class ControllerFinder
{
    private Logger itsLogger = Logger.getLogger(getClass().getName());
    private Set<String> itsPackagesToSearch;

    /** Create a ControllerFinder that will search the given packages for the controller.
     * @param packagesToSearch the set of package names to search
     */
    public ControllerFinder(Set<String> packagesToSearch)
    {
        itsPackagesToSearch = packagesToSearch;
    }

    /** Finds the named class in the list of packages provided to the constructor.
     * @param modelName the name of the model whose controller we are locating
     * @return the class matching the controller name, or null if it couldn't be found.  The controller must
     * extend {@link ApplicationController}.
     */
    public Class find(String modelName)
    {
        itsLogger.debug("Finding " + modelName);
        for (String packageName: itsPackagesToSearch)
        {
            String className = packageName + "." + modelName + "Controller";
            itsLogger.debug("Attempting to load " + className);
            try
            {
                Class clazz = Class.forName(className);
                if (ApplicationController.class.isAssignableFrom(clazz))
                {
                    return clazz;
                }
                else
                {
                    if (itsLogger.isDebugEnabled())
                    {
                        itsLogger.debug(clazz.getName() + " is not a " + ApplicationController.class.getName());
                    }
                }
            }
            catch (ClassNotFoundException e)
            {
                itsLogger.debug(className + " not found");
            }
        }
        return null;
    }

}
