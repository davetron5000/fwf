package com.naildrivin5.fwf;

import java.util.*;

/** Provides straightforward access to request parameters.
 */
public class Parameters extends HashMap<String,Object>
{
    /** Returns true if the given parameter was specified in the request .
     * @param parameter the name of the parameter.
     * @return true if the parameter was provided <b>with a value</b>, false otherwise.  Empty strings are considered the same
     * as null/no value.
     */
    public boolean contains(String parameter)
    {
        return super.containsKey(parameter);
    }

    /** Puts the given key in the parameter map with the given value.
     * @param key the key
     * @param value null, a string, or a list of string, representing the values.
     * @return the previous value associated with this key
     * @throws IllegalArgumentException if value is not of a proper type
     */
    public Object put(String key, Object value)
    {
        if ( (value == null) || (value instanceof String) || (value instanceof List) )
            return super.put(key,value);
        throw new IllegalArgumentException(value.getClass().getName() + " is not allowed in a " + getClass().getName());
    }

    /** Gets the value of the parameter as a string.
     * @param param the parameter value
     *
     * @return the parameter as a string, or null if it wasn't in the map.  If there were multiple values,
     * returns the first one parsed (which is largely undefined)
     */
    @SuppressWarnings("unchecked")
    public String getString(String param)
    {
        Object value = get(param);
        if (value == null)
            return null;
        if (value instanceof String)
            return (String)value;
        else 
            return ((List<String>)value).get(0);
    }

    /** Gets the value of the parameter as a list.
     * @param param the param in question.
     * @return null if the param isn't in the parameters, a List otherwise.
     * The List will contain at least one item.
     */
    @SuppressWarnings("unchecked")
    public List<String> getAsList(String param)
    {
        Object value = get(param);
        if (value == null)
            return null;
        if (value instanceof List)
            return (List<String>)value;
        if (value instanceof String)
        {
            List<String> simple = new ArrayList<String>(1);
            simple.add((String)value);
            return simple;
        }
        throw new IllegalStateException(getClass().getName() + " shouldn't have any " + value.getClass().getName() + " objects in it");
    }
}
