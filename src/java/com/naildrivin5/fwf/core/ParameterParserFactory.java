package com.naildrivin5.fwf.core;

import java.util.*;

/** Provides access to {@link ParameterParser} instances.
 */
public class ParameterParserFactory
{
    private ParameterParserFactory() 
    {
    }

    /** Returns a new default implementation of {@link ParameterParser}.
     * @return a ParameterParser.
     */
    public static ParameterParser create()
    {
        return new ParameterParser();
    }
}
