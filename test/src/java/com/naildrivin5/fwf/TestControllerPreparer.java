
package com.naildrivin5.fwf;

import java.util.*;

import org.testng.*;
import org.testng.annotations.*;

import static org.easymock.classextension.EasyMock.*;

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
