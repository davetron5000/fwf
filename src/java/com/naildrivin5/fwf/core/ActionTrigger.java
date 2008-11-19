package com.naildrivin5.fwf.core;

import java.lang.reflect.*;
import java.text.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.naildrivin5.fwf.*;

import org.apache.log4j.*;

/** This triggers the actions based on the request.
*/
public class ActionTrigger
{
    private Logger itsLogger = Logger.getLogger(getClass().getName());

    /** Triggers the action based on the request and response.
     * @param controller the controller on which to invoke the action
     * @param actionName the name of the action to trigger
     */
    public void trigger(ApplicationController controller, String actionName)
    {
        Class clazz = controller.getClass();

        if (itsLogger.isDebugEnabled())
            itsLogger.debug("Triggering " + actionName + " on a " + clazz.getName());

        try
        {
            Method method = clazz.getMethod(actionName,new Class[0]);
            if (method != null)
            {
                itsLogger.debug("Found method on class");
                method.invoke(controller);
            }
            else
            {
                itsLogger.debug("No method on class");
            }
        }
        catch (InvocationTargetException e)
        {
            itsLogger.warn(MessageFormat.format("Got exception invoking {0} on a {1}",actionName,clazz.getName()),e);
        }
        catch (IllegalAccessException e)
        {
            itsLogger.warn(MessageFormat.format("Problem invoking {0} on a {1}",actionName,clazz.getName()),e);
        }
        catch (NoSuchMethodException e)
        {
            itsLogger.debug("Didn't find method " + actionName,e);
        }
    }
}
