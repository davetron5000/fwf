package com.naildrivin5.fwf;

import java.util.*;

import org.testng.*;
import org.testng.annotations.*;

@Test
public class TestParameterParser
{
    private static final String BLANK_PARAM = "blank";
    private static final String ALL_NULLS_PARAM = "allnulls";
    private static final String NULL_PARAM = "null";
    private ParameterParser itsParser;
    private Map itsSimpleMap;
    private int itsSimpleMapNumNonEmptyElements;
    private Map itsComplexMap;
    private int itsComplexMapNumNonEmptyElements;

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
        itsSimpleMap.put("id","99");
        itsSimpleMapNumNonEmptyElements++;
        itsSimpleMap.put("age","16");
        itsSimpleMapNumNonEmptyElements++;
        itsSimpleMap.put("default","true");
        itsSimpleMapNumNonEmptyElements++;
        itsSimpleMap.put(BLANK_PARAM,"");
        itsSimpleMap.put(NULL_PARAM,null);

        itsComplexMap = new HashMap();
        itsComplexMapNumNonEmptyElements = 0;
        itsComplexMap.put("foo",new String[] { "one","two","three" });
        itsComplexMapNumNonEmptyElements++;
        itsComplexMap.put("foobar",new String[] { "one",});
        itsComplexMapNumNonEmptyElements++;
        itsComplexMap.put("bar",new String[] { null,"two","three" });
        itsComplexMapNumNonEmptyElements++;
        itsComplexMap.put("baz",new String[] { "one",null,"three" });
        itsComplexMapNumNonEmptyElements++;
        itsComplexMap.put("quux",new String[] { null,null,"three" });
        itsComplexMapNumNonEmptyElements++;
        itsComplexMap.put(BLANK_PARAM,new String[0]);
        itsComplexMap.put(NULL_PARAM,null);
        itsComplexMap.put(ALL_NULLS_PARAM,new String[] { null,null,null,null,null});
    }
    public void testNullParametersResultsInEmptyMap()
    {
        Parameters params = itsParser.parse(null);

        Assert.assertEquals(params.size(),0);
    }

    public void testEmptyParametersResultsInEmptyMap()
    {
        Parameters params = itsParser.parse(new HashMap());
        Assert.assertEquals(params.size(),0);
    }

    public void testNullValueNotFound()
    {
        Parameters params = itsParser.parse(itsSimpleMap);
        assert !params.contains(NULL_PARAM) : "Expected contains() to be false for " + NULL_PARAM;
    }

    public void testBlankValueNotFound()
    {
        Parameters params = itsParser.parse(itsSimpleMap);
        assert !params.contains(BLANK_PARAM) : "Expected contains() to be false for " + BLANK_PARAM;
    }

    public void testIsEmpty()
    {
        Parameters params = itsParser.parse(itsSimpleMap);
        assert itsParser.isEmptyValue("") : "Expected empty string to be considered an empty value";
        assert itsParser.isEmptyValue("       ") : "Expected all-space string to be considered an empty value";
        assert itsParser.isEmptyValue("   \t    ") : "Expected all-whitespace string to be considered an empty value";
        assert itsParser.isEmptyValue(null) : "Expected null to be considered an empty value";
        assert itsParser.isEmptyValue(new String[0]) : "Expected empty array to be considered an empty value";
        assert itsParser.isEmptyValue(new String[1]) : "Expected array of nulls to be considered an empty value";
        assert itsParser.isEmptyValue(new String[256]) : "Expected big array of nulls to be considered an empty value";
        assert !itsParser.isEmptyValue("blah") : "Expected 'blah' to NOT be empty";
        String blah[] = {
            "foo",
            "bar",
            "baz",
            null,
            "blah"
        };
        assert !itsParser.isEmptyValue(blah) : "Expected array of values to NOT be empty";
    }

    public void testSimpleParameters()
    {
        Parameters params = itsParser.parse(itsSimpleMap);
        assertParamsMatchesMap(params,itsSimpleMap,itsSimpleMapNumNonEmptyElements);
    }

    public void testParametersWithArrays()
    {
        Parameters params = itsParser.parse(itsComplexMap);
        assertParamsMatchesMap(params,itsComplexMap,itsComplexMapNumNonEmptyElements);
    }

    public void testBadPuts()
    {
        Parameters params = itsParser.parse(itsComplexMap);
        try
        {
            params.put("blah",45);
            Assert.fail("Shouldn't be allowed to put an integer into a Parameters");
        }
        catch (IllegalArgumentException e) { }
        try
        {
            params.put("blah",new String[3]);
            Assert.fail("Shouldn't be allowed to put an ArrayList into a Parameters");
        }
        catch (IllegalArgumentException e) { }
        try
        {
            params.put("blah",true);
            Assert.fail("Shouldn't be allowed to put a boolean into a Parameters");
        }
        catch (IllegalArgumentException e) { }
    }

    private void assertParamsMatchesMap(Parameters params, Map map, int nonEmptyElements)
    {
        Assert.assertEquals(params.size(),nonEmptyElements);
        for (Object key: map.keySet())
        {
            Object value = map.get(key);
            String param = (String)key;
            if (!itsParser.isEmptyValue(value))
            {
                assert params.contains(param) : "Expected " + param + " to be in the Parameters";
                if (value instanceof String)
                {
                    String paramValue = (String)value;
                    Assert.assertEquals(params.getString(param),paramValue);
                    Assert.assertEquals(params.getAsList(param).size(),1);
                    Assert.assertEquals(params.getAsList(param).get(0),paramValue);
                }
                else if (value instanceof String[])
                {
                    assertSameElements((String[])value,params.getAsList(param));
                }
            }
            else
            {
                assert !params.contains(param) : "Shouldn't contain a value it considered empty: '" + value.toString() + "'";
            }
        }
    }

    private void assertSameElements(String []values, Collection<String> setOfValues)
    {
        Assert.assertEquals(setOfValues.size(),values.length);
        for (String paramValue: values)
        {
            assert setOfValues.contains(paramValue) : "Didn't contain '" + paramValue + "'";
        }
    }

}
