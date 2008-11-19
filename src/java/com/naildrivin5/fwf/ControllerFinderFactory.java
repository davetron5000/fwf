package com.naildrivin5.fwf;

import java.util.*;

/** Provides access to {@link ControllerFinder} instances.
 */
public class ControllerFinderFactory
{
    private ControllerFinderFactory() 
    {
    }

    /** Returns a new default implementation of {@link ControllerFinder}.
     * @param packageListString a list of comma-delimited packages to search.  Whitespace is trimmed.
     * @return a ControllerFinder.
     */
    public static ControllerFinder create(String packageListString)
    {
        String packages[] = packageListString.split(",");
        Set<String> packageNames = new HashSet<String>();
        for (String packageName : packages)
        {
            packageNames.add(packageName.trim());
        }
        return new ControllerFinder(packageNames);
    }
}
