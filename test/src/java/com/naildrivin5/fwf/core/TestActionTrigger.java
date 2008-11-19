package com.naildrivin5.fwf.core;

import com.naildrivin5.fwf.*;

import java.util.*;

import org.testng.*;
import org.testng.annotations.*;

import static org.easymock.classextension.EasyMock.*;

@Test
public class TestActionTrigger
{
    private ActionTrigger itsActionTrigger;

    @BeforeTest
    public void setUp()
    {
        itsActionTrigger = new ActionTrigger();
    }

    public void testTriggerTwoMethod()
    {
        ProjectController controller = createMock(ProjectController.class);
        controller.show();
        replay(controller);

        itsActionTrigger.trigger(controller,"show");

        verify(controller);
    }

    public void testTriggerBasic()
    {
        ProjectController controller = createMock(ProjectController.class);
        controller.update();
        replay(controller);

        itsActionTrigger.trigger(controller,"update");

        verify(controller);
    }

    public void testTriggerNoMethod()
    {
        ProjectController controller = createMock(ProjectController.class);
        replay(controller);

        itsActionTrigger.trigger(controller,"doit");

        verify(controller);
    }
}
