<%@ page
import="java.util.*"
import="com.naildrivin5.fwf.demo.*"
%>
<%! private <T> T get(String name, HttpServletRequest request, Class<T> clazz)
    {
        Object value = request.getParameter(name);
        if (value == null)
            value = request.getAttribute(name);

        return (T)value;
    }
%>
<h1>All Projects here</h1>
<%= get("blah",request,String.class) %>
<ul>
<%
List<Project> projects = get("projects",request,List.class);
if (projects != null)
{
    for (Project p: projects)
    {
        %>
        <li><b><%= p.getName() %></b> - <%= p.getDescription() %></li>
        <%
    }
}
%>
</ul>
