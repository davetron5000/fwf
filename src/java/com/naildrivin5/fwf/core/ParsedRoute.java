package com.naildrivin5.fwf.core;

/** Represents a parsed route in terms of a model, id, and action.
*/
public class ParsedRoute
{
    private String itsModelName;
    private Long itsId;
    private String itsActionName;

    /** Create a new ParsedRoute.
     * @param modelName the name of the model
     * @param id the id included (or null if it wasn't)
     * @param actionName the name of the action
     */
    public ParsedRoute(String modelName, Long id, String actionName)
    {
        itsModelName = modelName;
        itsId = id;
        itsActionName = actionName;
    }

    public String getModelName() 
    {
        return itsModelName; 
    }

    public Long getId() 
    {
        return itsId; 
    }

    public String getActionName() 
    {
        return itsActionName; 
    }

    /** Returns a string representation suitable for debugging/log messages.
     * @return string representation.
     */
    public String toString()
    {
        return getModelName() + " for id " + getId() + ", action: " + getActionName();
    }
}
