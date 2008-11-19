package com.naildrivin5.fwf.core;

import java.util.*;

import com.naildrivin5.fwf.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;

/** Prepares a controller for performing an action.
 */
public class ControllerPreparer
{
    private Logger itsLogger = Logger.getLogger(getClass().getName());

    /** Prepares the controller for an action.
     * @param controller the controller in question.
     * @param id the id provided, or null if none was
     * @param parameters the parameters parsed from the request.
     */
    public void prepare(ApplicationController controller, Long id, Parameters parameters)
    {
        controller.setId(id);
        controller.setParams(parameters);
        controller.setResponse(new Response());

        ConvertingWrapDynaBean bean = new ConvertingWrapDynaBean(controller);

        for (String key: parameters.keySet())
        {
            String valueAsString = parameters.getString(key);
            List<String> valueAsList = parameters.getAsList(key);
            if (valueAsList == null)
                continue;

            Object value;
            if (valueAsList.size() == 1)
                value = valueAsString;
            else
                value = valueAsList;

            String loggerMessage = null;
            if (itsLogger.isDebugEnabled())
            {
                loggerMessage = "Setting " + key + " of type " + (value == null ? "NULL" : value.getClass().getName()) + " on a " + getClass().getName();
                itsLogger.debug(loggerMessage);
            }

            try
            {
                bean.set(key,value);
            }
            catch (ConversionException ex)
            {
                itsLogger.debug(loggerMessage,ex);
            }
            catch (IllegalArgumentException ex)
            {
                itsLogger.debug(loggerMessage,ex);
            }
            catch (NullPointerException ex)
            {
                itsLogger.debug(loggerMessage,ex);
            }
        }

    }
}
