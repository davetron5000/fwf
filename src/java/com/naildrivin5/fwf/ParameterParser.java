package com.naildrivin5.fwf;

import java.text.*;
import java.util.*;

import org.apache.log4j.*;

/** Parses parameters from {@link javax.servlet.ServletRequest} into a more useful class.
 */
public class ParameterParser
{
    private Logger itsLogger = Logger.getLogger(getClass().getName());

    /** Parse the parameter map, as defined by {@link javax.servlet.ServletRequest#getParameterMap()}.
     * @param parameterMap a map of string to either string or string array, representing the parameters
     * included with an HTTP request.  Null is allowed.
     * @return A valid Parameters, regardless of input
     */
    public Parameters parse(Map parameterMap)
    {
        Parameters params = new Parameters();
        if ( (parameterMap == null) || (parameterMap.size() == 0) )
            return params;

        for (Object key: parameterMap.keySet())
        {
            String param = (String)key;
            Object value = parameterMap.get(key);
            if (!isEmptyValue(value))
            {
                if (itsLogger.isDebugEnabled())
                    itsLogger.debug(MessageFormat.format("Putting ''{0}'' as param with value ''{1}''",param,value.toString()));
                if (value instanceof String)
                {
                    params.put(param,(String)value);
                }
                else if (value instanceof String[])
                {
                    params.put(param,Arrays.asList((String[])value));
                }
                else
                {
                    itsLogger.warn("Got a " + value.getClass().getName() + " for parameter " + param + " when we were only expecting String or String[]");
                }
            }
        }
        return params;
    }

    /** Returns true if the value passed in is considered to be "no value".
     * @param value the value of a parameter to test.
     * @return true if the value is null, all white-space, an empty array, or an array of nulls.
     */
    protected boolean isEmptyValue(Object value)
    {
        if (value == null)
        {
            itsLogger.debug("null value is considered empty");
            return true;
        }
        if (value instanceof String)
        {
            boolean empty = ((String)value).trim().length() == 0;
            if (itsLogger.isDebugEnabled())
                itsLogger.debug("'" + value.toString() + "' is considered " + (empty ? "empty" : "non-empty") );
            return empty;
        }
        else if (value instanceof String[])
        {
            return isArrayEmpty((String[])value);
        }
        else
        {
            if (itsLogger.isDebugEnabled())
                itsLogger.debug(value.getClass().getName() + "'s are considered non-empty");
            return false;
        }
    }

    private boolean isArrayEmpty(String[] array)
    {
        if (array.length == 0)
        {
            itsLogger.debug("Empty arrays are considered empty values");
            return true;
        }
        int numNulls = 0;
        for (String item: array)
        {
            if (item == null)
                numNulls++;
        }
        boolean empty = numNulls == array.length;
        if (empty)
            itsLogger.debug("Arrays with all nulls are considered empty");
        else
            itsLogger.debug("Arrays with only some nulls are not considered empty");
        return empty;
    }
}
