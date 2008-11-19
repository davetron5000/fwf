package com.naildrivin5.fwf.core;

/** Represents a parsed route in terms of a controller, id, and action.
*/
public class ParsedRoute
{
    private String itsControllerName;
    private Long itsId;
    private String itsActionName;

    /** Create a new ParsedRoute.
     * @param controllerName the name of the controller
     * @param id the id included (or null if it wasn't)
     * @param actionName the name of the action
     */
    public ParsedRoute(String controllerName, Long id, String actionName)
    {
        itsControllerName = controllerName;
        itsId = id;
        itsActionName = actionName;
    }

    public String getControllerName() 
    {
        return itsControllerName; 
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
        return getControllerName() + " for id " + getId() + ", action: " + getActionName();
    }
}
