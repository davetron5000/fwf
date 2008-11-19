package com.naildrivin5.fwf.core;

import com.naildrivin5.fwf.*;

import java.util.*;

import org.testng.*;
import org.testng.annotations.*;

@Test
public class TestRouteParser
{
    private RouteParser itsParser;

    @DataProvider(name = "simpleTestCases")
    private Object[][] getSimpleTestCases()
    {
        Object testCases[][] = new Object[15 * 2][2];

        long id = 45;
        String noun = "project";
        String controller = "Project";
        int row = 0;
        for (int i=0;i<2; i++)
        {
            id *= (i + 1);
            if (i == 1)
            {
                noun = "big_stuff";
                controller = "BigStuff";
            }

            testCases[row][0] = new TestRequest("GET",noun + "");
            testCases[row][1] = new ParsedRoute(controller,null,"index");
            row++;

            testCases[row][0] = new TestRequest("GET",noun + "/" + id + "");
            testCases[row][1] = new ParsedRoute(controller,id,"show");
            row++;

            testCases[row][0] = new TestRequest("PUT",noun + "/" + id + "");
            testCases[row][1] = new ParsedRoute(controller,id,"update");
            row++;

            testCases[row][0] = new TestRequest("DELETE",noun + "/" + id + "");
            testCases[row][1] = new ParsedRoute(controller,id,"destroy");
            row++;

            testCases[row][0] = new TestRequest("POST",noun + "");
            testCases[row][1] = new ParsedRoute(controller,null,"create");
            row++;

            testCases[row][0] = new TestRequest("GET",noun + "/" + id + "/show");
            testCases[row][1] = new ParsedRoute(controller,id,"show");
            row++;

            testCases[row][0] = new TestRequest("PUT",noun + "/" + id + "/update");
            testCases[row][1] = new ParsedRoute(controller,id,"update");
            row++;

            testCases[row][0] = new TestRequest("DELETE",noun + "/" + id + "/destroy");
            testCases[row][1] = new ParsedRoute(controller,id,"destroy");
            row++;

            testCases[row][0] = new TestRequest("GET",noun + "/" + id + "/frobnosticate");
            testCases[row][1] = new ParsedRoute(controller,id,"frobnosticate");
            row++;

            testCases[row][0] = new TestRequest("GET",noun + "/" + id + "/do_this_now");
            testCases[row][1] = new ParsedRoute(controller,id,"doThisNow");
            row++;

            testCases[row][0] = new TestRequest("PUT",noun + "/" + id + "/frobnosticate");
            testCases[row][1] = new ParsedRoute(controller,id,"frobnosticate");
            row++;

            testCases[row][0] = new TestRequest("POST",noun + "/" + id + "/frobnosticate");
            testCases[row][1] = new ParsedRoute(controller,id,"frobnosticate");
            row++;

            testCases[row][0] = new TestRequest("DELETE",noun + "/" + id + "/frobnosticate");
            testCases[row][1] = new ParsedRoute(controller,id,"frobnosticate");
            row++;

            testCases[row][0] = new TestRequest("GET",noun + "/asdfasdfasdf/frobnosticate");
            testCases[row][1] = null;
            row++;

            testCases[row][0] = new TestRequest("bleorgh",noun + "/45/frobnosticate");
            testCases[row][1] = null;
            row++;
        }
        return testCases;
    }

    @BeforeTest
    public void setUp()
    {
        itsParser = RouteParserFactory.create();
    }

    @Test(dataProvider = "simpleTestCases")
    public void testNormalCases(TestRequest request, ParsedRoute route)
    {
        ParsedRoute parsedRoute = itsParser.parse(request.method,request.url);
        if (parsedRoute == null)
        {
            assert route == null : "Got null for " + request.toString() + " but we expected " + route.toString();
        }
        else if (route == null)
        {
            Assert.fail("Expected null for " + request.toString() + ", but got " + parsedRoute.toString());
        }
        else
        {
            Assert.assertEquals(parsedRoute.getModelName(),route.getModelName());
            Assert.assertEquals(parsedRoute.getId(),route.getId());
            Assert.assertEquals(parsedRoute.getActionName(),route.getActionName());
        }
    }

    private class TestRequest
    {
        String method;
        String url;

        public TestRequest(String method, String url)
        {
            this.method = method;
            this.url = url;
        }

        public String toString()
        {
            return method + " " + url;
        }
    }
}
