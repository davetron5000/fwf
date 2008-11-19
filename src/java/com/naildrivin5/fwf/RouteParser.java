package com.naildrivin5.fwf;

import java.text.*;
import java.util.*;

import org.apache.log4j.*;

import static com.naildrivin5.fwf.RequestMethod.*;

/** Parses parameters from {@link javax.servlet.ServletRequest} into a more useful class.
 */
public class RouteParser
{
    private Logger itsLogger = Logger.getLogger(getClass().getName());

    /** Parses the given uri and HTTP request method into a parsed route.
     * @param request the request method
     * @param uri the uri requested
     * @return a ParsedRoute
     */
    public ParsedRoute parse(String request, String uri)
    {
        if (uri == null)
            throw new IllegalArgumentException("null uris are not allowed");
        if (request == null)
            throw new IllegalArgumentException("null request methods are not allowed");

        RequestMethod method = RequestMethod.fromString(request);
        if (method == null)
            throw new IllegalArgumentException(request + " is not a known request method");

        String parts[] = uri.split("\\/");
        if (parts.length == 0)
            throw new IllegalArgumentException("uris with nothing in them aren't allowed");

        String controllerName = getControllerName(parts[0]);
        Long id = null;
        String actionName = null;
        if (parts.length > 1)
        {
            id = parseId(parts[1]);
            if (parts.length > 2)
            {
                actionName = getActionName(parts[2]);
            }
        }
        if (actionName == null)
        {
            actionName = getActionNameFromRequestMethod(method,id);
        }

        return new ParsedRoute(controllerName,id,actionName);
    }

    /** Translates the action name in the URI to one we can use to look up
     * the action in the controller.
     * @param actionNameUriPart action name from the URI (may be null)
     * @return a normalized version of the action name we can use to locate
     * it on the controller.
     */
    protected String getActionName(String actionNameUriPart)
    {
        if (actionNameUriPart == null)
            return null;
        return StringUtils.lcfirst(StringUtils.camelize(actionNameUriPart));
    }

    /** Determines the action name from the request method and id.
     * @param method the HTTP request method.
     * @param id the id included in the request, or null if it wasn't included.
     * @return a normalized action name that can be used on the controller
     */
    protected String getActionNameFromRequestMethod(RequestMethod method, Long id)
    {
        switch (method)
        {
            case GET :
                if (id == null)
                    return "index";
                else
                    return "show";
            case PUT :
                return "update";
            case POST :
                return "create";
            case DELETE :
                return "destroy";
            default:
                return null;
        }

    }

    /** Parses the id from the URL string.
     * @param idString the id part of the URI, which should be a number.
     * @return a long representing the idString or null if it was missing or couldn't be parsed.
     */
    protected Long parseId(String idString)
    {
        if (idString == null)
            return null;
        try
        {
            return Long.parseLong(idString);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /** Gets the normalized version of the controller based on the URI fragment.
     * @param controllerUriPart the controller name from the URI.
     * @return a normalized name for the controller we can use to locate its implementation.
     */
    protected String getControllerName(String controllerUriPart)
    {
        if (controllerUriPart == null)
            return null;
        return StringUtils.camelize(controllerUriPart) + "Controller";
    }
}
