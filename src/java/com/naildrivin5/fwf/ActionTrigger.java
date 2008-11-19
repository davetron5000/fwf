package com.naildrivin5.fwf;

import javax.servlet.*;
import javax.servlet.http.*;

/** This triggers the actions based on the request.
*/
public class ActionTrigger
{
    private ControllerFinder itsControllerFinder;
    private ParameterParser itsParameterParser;
    private RouteParser itsRouteParser;

    /** Triggers the action based on the request and response.
     * @param request the request sent to the servlet.
     * @param response the response provided by the servlet
     */
    public void trigger(HttpServletRequest request, HttpServletResponse response)
    {
        Parameters params = itsParameterParser.parse(request.getParameterMap());
        ParsedRoute route = itsRouteParser.parse(request.getMethod(),request.getPathInfo());
        Class controllerClass = itsControllerFinder.find(route.getControllerName());
    }

    public RouteParser getRouteParser() 
    {
        return itsRouteParser; 
    }

    public void setRouteParser(RouteParser i) 
    {
        itsRouteParser = i; 
    }

    public ParameterParser getParameterParser() 
    {
        return itsParameterParser; 
    }

    public void setParameterParser(ParameterParser i) 
    {
        itsParameterParser = i; 
    }

    public ControllerFinder getControllerFinder() 
    {
        return itsControllerFinder; 
    }

    public void setControllerFinder(ControllerFinder i) 
    {
        itsControllerFinder = i; 
    }

}
