package com.naildrivin5.fwf.core;

import com.naildrivin5.fwf.*;

import java.util.*;

import org.testng.*;
import org.testng.annotations.*;

@Test
public class TestControllerFinder
{
    @DataProvider(name = "testCases")
    public Object[][] getTestCases()
    {
        Object testCases[][] = new Object[5][3];

        int row = 0;
        testCases[row][0] = "Task";
        testCases[row][1] = "com.naildrivin5.fwf,com.naildrivin5.fwf.testcontrollers";
        testCases[row][2] = "com.naildrivin5.fwf.testcontrollers.TaskController";
        row++;

        testCases[row][0] = "Task";
        testCases[row][1] = "com.naildrivin5.fwf.testcontrollers";
        testCases[row][2] = "com.naildrivin5.fwf.testcontrollers.TaskController";
        row++;

        testCases[row][0] = "Task";
        testCases[row][1] = "com.naildrivin5.fwf";
        testCases[row][2] = null;
        row++;

        testCases[row][0] = "Project";
        testCases[row][1] = "com.naildrivin5.fwf,com.naildrivin5.fwf.testcontrollers";
        testCases[row][2] = "com.naildrivin5.fwf.ProjectController";
        row++;

        testCases[row][0] = "Project";
        testCases[row][1] = "com.naildrivin5.fwf.testcontrollers";
        testCases[row][2] = null;
        row++;

        return testCases;
    }

    @Test(dataProvider = "testCases")
    public void testControllerLookup(String controllerName, String packageList, String expectedClassName)
    {
        ControllerFinder finder = ControllerFinderFactory.create(packageList);
        Class clazz = finder.find(controllerName);
        if (expectedClassName == null)
        {
            assert clazz == null : "Expected not to find " + controllerName + ", but found " + clazz.getName();
        }
        else
        {
            Assert.assertEquals(clazz.getName(),expectedClassName);
            assert ApplicationController.class.isAssignableFrom(clazz);
        }
    }
}
