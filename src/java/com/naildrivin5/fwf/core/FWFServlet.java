package com.naildrivin5.fwf.core;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.naildrivin5.fwf.*;

/** The main servlet that implements the Fun Web Framework.
 */
public class FWFServlet extends HttpServlet
{
    private static final String METHOD_PARAM = "_method";
    private static final String METHOD_HEADER = "X-HTTP-Method-Override";

    private RouteParser itsRouteParser;
    private ParameterParser itsParameterParser;
    private ControllerFinder itsControllerFinder;
    private ControllerPreparer itsControllerPreparer;
    private ActionTrigger itsActionTrigger;
    private ViewFinder itsViewFinder;

    /** Initializes various things. 
     * @throws ServletException if there was a problem initializing things
     * */
    public void init()
        throws ServletException
    {
        String packageList = getInitParameter("controllerPackageList");
        if (packageList == null)
            throw new ServletException("You must specify controllerPackageList to a list of one or more packages as an init parameter");

        itsRouteParser = RouteParserFactory.create();
        itsParameterParser = ParameterParserFactory.create();
        itsControllerFinder = ControllerFinderFactory.create(packageList);
        itsControllerPreparer = new ControllerPreparer();
        itsActionTrigger = new ActionTrigger();
        itsViewFinder = new ViewFinder();
    }

    /** Handles all requests.
     * @param request the request
     * @param response the response
     * @throws ServletException if there was some non-IO related problem
     * @throws IOException if there was an IO-related problem
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String method = determineMethod(request);

        ParsedRoute route = itsRouteParser.parse(method,request.getPathInfo());
        if (route == null)
            throw new ServletException("No route for " + method + " " + request.getPathInfo());
        Class controllerClass = itsControllerFinder.find(route.getModelName());
        Parameters params = itsParameterParser.parse(request.getParameterMap());
        ApplicationController controller = instantiateController(controllerClass);

        itsControllerPreparer.prepare(controller,route.getId(),params);
        itsActionTrigger.trigger(controller,route.getActionName());

        String path = itsViewFinder.find(route.getModelName(),route.getActionName(),controller.response());

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
        dispatcher.forward(request,response);
    }

    private ApplicationController instantiateController(Class clazz)
        throws ServletException
    {
        try
        {
            return (ApplicationController)clazz.newInstance();
        }
        catch (IllegalAccessException e)
        {
            throw new ServletException(e);
        }
        catch (InstantiationException e)
        {
            throw new ServletException(e);
        }
    }

    /** Handles finding out the request method, since some clients cannot use HTTP.
     * @param request the HttpServletRequest that came with the reuqest
     * @return the method requested
     */
    protected String determineMethod(HttpServletRequest request)
    {
        String method = request.getMethod();
        if (request.getParameter(METHOD_PARAM) != null)
            return request.getParameter(METHOD_PARAM).toLowerCase();
        else if (request.getHeader(METHOD_HEADER) != null)
            return request.getHeader(METHOD_HEADER).toLowerCase();
        else
            return method.toLowerCase();
    }
}

