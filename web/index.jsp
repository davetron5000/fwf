<%@ page 
    import="com.naildrivin5.fwf.*;"
%>
<%! Dummy dummy = new Dummy("Bleorgh"); %>
<%
    if (request.getParameter("blah") != null)
    {
        dummy.setD(request.getParameter("blah").toUpperCase());
    }
%>
<html>
<head><title>Dummy</title></head>
<body>
<h1>Dummy Index</h1>
Das timen ist: <%= new java.util.Date().toString() %>
<hr />
The value of dummy is <%= dummy.getD() %>
</body>
</html>
