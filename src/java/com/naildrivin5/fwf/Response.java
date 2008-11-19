package com.naildrivin5.fwf;

import java.util.*;

/** Provides a means to control the response.
 */
public class Response
{
    private String itsViewName;
    private String itsModelName;

    /** Renders the given view, using the implied model.
     * @param viewName the name of the view, without extensions, etc.
     */
    public void render(String viewName)
    {
        itsViewName = viewName;
    }

    /** Renders the given view of the given model.
     * @param modelName the name of the model.
     * @param viewName the name of the view.
     */
    public void render(String modelName, String viewName)
    {
        itsViewName = viewName;
        itsModelName = modelName;
    }

    public String getViewName() 
    {
        return itsViewName;
    }

    public String getModelName()
    {
        return itsModelName;
    }
}
