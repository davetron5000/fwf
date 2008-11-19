package com.naildrivin5.fwf;

import java.util.*;

/** Provides access to {@link RouteParser} instances.
 */
public class RouteParserFactory
{
    private RouteParserFactory() 
    {
    }

    /** Returns a new default implementation of {@link RouteParser}.
     * @return a RouteParser.
     */
    public static RouteParser create()
    {
        return new RouteParser();
    }
}
