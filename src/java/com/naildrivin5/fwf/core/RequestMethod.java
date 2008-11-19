package com.naildrivin5.fwf.core;

import java.util.*;

/** HTTP Request methods.
 */
public enum RequestMethod
{
    /** HTTP GET. */
    GET("get"),
    /** HTTP POST. */
    POST("post"),
    /** HTTP PUT. */
    PUT("put"),
    /** HTTP DELETE. */
    DELETE("delete");

    private String itsString;
    private RequestMethod(String string)
    {
        itsString = string.toLowerCase().trim();
    }

    /** Given a provided request method string, returns the matching enum.
     * @param string the string to lookup.  Case and padding of whitespace don't matter.
     * @return a valid RequestMethod or null if the string didn't match.
     */
    public static RequestMethod fromString(String string)
    {
        if (string == null)
            return null;
        String normalized = string.toLowerCase().trim();
        for (RequestMethod method: EnumSet.allOf(RequestMethod.class))
        {
            if (normalized.equals(method.itsString))
                return method;
        }
        return null;
    }
}
