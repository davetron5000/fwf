package com.naildrivin5.fwf;

import java.util.*;

import org.testng.*;
import org.testng.annotations.*;

@Test
public class TestRouteParser
{
    private RouteParser itsParser;

    @DataProvider(name = "testCases")
    private Object[][] getTestCases()
    {
        Object testCases[][] = new Object[12][2];
        int row = 0;

        testCases[row][0] = new TestRequest("GET","project");
        testCases[row][1] = new ParsedRoute("ProjectController",null,"index");
        row++;

        testCases[row][0] = new TestRequest("GET","project/45");
        testCases[row][1] = new ParsedRoute("ProjectController",45L,"show");
        row++;

        testCases[row][0] = new TestRequest("PUT","project/45");
        testCases[row][1] = new ParsedRoute("ProjectController",45L,"update");
        row++;

        testCases[row][0] = new TestRequest("DELETE","project/45");
        testCases[row][1] = new ParsedRoute("ProjectController",45L,"destroy");
        row++;

        testCases[row][0] = new TestRequest("POST","project");
        testCases[row][1] = new ParsedRoute("ProjectController",null,"create");
        row++;

        testCases[row][0] = new TestRequest("GET","project/45/show");
        testCases[row][1] = new ParsedRoute("ProjectController",45L,"show");
        row++;

        testCases[row][0] = new TestRequest("PUT","project/45/update");
        testCases[row][1] = new ParsedRoute("ProjectController",45L,"update");
        row++;

        testCases[row][0] = new TestRequest("DELETE","project/45/destroy");
        testCases[row][1] = new ParsedRoute("ProjectController",45L,"destroy");
        row++;

        testCases[row][0] = new TestRequest("GET","project/45/frobnosticate");
        testCases[row][1] = new ParsedRoute("ProjectController",45L,"frobnosticate");
        row++;

        testCases[row][0] = new TestRequest("PUT","project/45/frobnosticate");
        testCases[row][1] = new ParsedRoute("ProjectController",45L,"frobnosticate");
        row++;

        testCases[row][0] = new TestRequest("POST","project/45/frobnosticate");
        testCases[row][1] = new ParsedRoute("ProjectController",45L,"frobnosticate");
        row++;

        testCases[row][0] = new TestRequest("DELETE","project/45/frobnosticate");
        testCases[row][1] = new ParsedRoute("ProjectController",45L,"frobnosticate");
        row++;

        return testCases;
    }

    @BeforeTest
    public void setUp()
    {
        itsParser = RouteParserFactory.create();
    }

    @Test(dataProvider = "testCases")
    public void testNormalCases(TestRequest request, ParsedRoute route)
    {
        ParsedRoute parsedRoute = itsParser.parse(request.method,request.url);
        Assert.assertEquals(parsedRoute.getControllerName(),route.getControllerName());
        Assert.assertEquals(parsedRoute.getId(),route.getId());
        Assert.assertEquals(parsedRoute.getActionName(),route.getActionName());
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
