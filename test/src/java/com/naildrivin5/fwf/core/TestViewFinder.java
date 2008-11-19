package com.naildrivin5.fwf.core;

import com.naildrivin5.fwf.*;

import java.util.*;

import org.testng.*;
import org.testng.annotations.*;

@Test
public class TestViewFinder
{
    private ViewFinder itsFinder;

    @BeforeTest
    public void setUp()
    {
        itsFinder = new ViewFinder();
    }

    @DataProvider(name = "testCases")
    public Object[][] getTestCases()
    {
        Object[][] testCases = new Object[4][3];

        int row = 0;

        testCases[row][0] = "Project";
        testCases[row][1] = "update";
        testCases[row][2] = "view/Project/update.jsp";
        row++;

        testCases[row][0] = "Project";
        testCases[row][1] = "doIt";
        testCases[row][2] = "view/Project/doIt.jsp";
        row++;

        testCases[row][0] = "TaskList";
        testCases[row][1] = "doIt";
        testCases[row][2] = "view/TaskList/doIt.jsp";
        row++;

        testCases[row][0] = "TaskList";
        testCases[row][1] = "edit";
        testCases[row][2] = "view/TaskList/edit.jsp";
        row++;

        return testCases;
    }

    @Test(dataProvider = "testCases")
    public void testViewFinder(String controllerName, String actionName, String expectedJSPPath)
    {
        String path = itsFinder.find(controllerName,actionName);

        Assert.assertEquals(path,expectedJSPPath);
    }
}
