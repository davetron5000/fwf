package com.naildrivin5.fwf.core;

import com.naildrivin5.fwf.*;

import java.util.*;

import org.testng.*;
import org.testng.annotations.*;

import static org.easymock.classextension.EasyMock.*;

import com.naildrivin5.fwf.Parameters;

@Test
public class TestControllerPreparer
{
    private ParameterParser itsParser;
    private Map itsSimpleMap;
    private int itsSimpleMapNumNonEmptyElements;
    private Parameters itsParameters;

    @BeforeTest
    @SuppressWarnings("unchecked")
    public void setUp()
    {
        itsParser = ParameterParserFactory.create();
        itsSimpleMap = new HashMap();
        itsSimpleMapNumNonEmptyElements = 0;
        itsSimpleMap.put("foo","bar");
        itsSimpleMapNumNonEmptyElements++;
        itsSimpleMap.put("crud","blah");
        itsSimpleMapNumNonEmptyElements++;
        itsSimpleMap.put("age","16");
        itsSimpleMapNumNonEmptyElements++;
        itsSimpleMap.put("taskNames",new String[] { "do this","do that","do something else"});
        itsParameters = itsParser.parse(itsSimpleMap);
    }

    public void testSimpleCase()
    {
        ApplicationController controller = createMock(ApplicationController.class);

        controller.setId(99L);
        controller.setParams(itsParameters);
        replay(controller);

        ControllerPreparer preparer = new ControllerPreparer();

        preparer.prepare(controller,99L,itsParameters);
        verify(controller);
    }

    public void testArraySet()
    {
        ProjectController controller = new ProjectController();

        ControllerPreparer preparer = new ControllerPreparer();

        preparer.prepare(controller,99L,itsParameters);

        List<String> names = controller.getTaskNames();
        String expectedNames[] = (String[])itsSimpleMap.get("taskNames");

        Assert.assertEquals(expectedNames.length,names.size());

        for (String name: expectedNames)
        {
            assert names.contains(name) : "Expected " + name + " to be in our list";
        }
    }

    public void testSimpleCaseNoId()
    {
        ApplicationController controller = createMock(ApplicationController.class);

        controller.setId(null);
        controller.setParams(itsParameters);
        replay(controller);

        ControllerPreparer preparer = new ControllerPreparer();

        preparer.prepare(controller,null,itsParameters);
        verify(controller);
    }

    public void testControllerWithSomeSetters()
    {
        ProjectController controller = createMock(ProjectController.class);

        controller.setId(134L);
        controller.setParams(itsParameters);
        controller.setFoo("bar");
        controller.setAge(16L);

        replay(controller);

        ControllerPreparer preparer = new ControllerPreparer();
        preparer.prepare(controller,134L,itsParameters);

        verify(controller);
    }
}
