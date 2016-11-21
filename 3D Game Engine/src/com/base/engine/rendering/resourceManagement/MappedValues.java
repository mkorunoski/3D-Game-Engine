package com.base.engine.rendering.resourceManagement;

import com.base.engine.core.Vector3f;

import java.util.HashMap;

public abstract class MappedValues
{
    HashMap<String, Vector3f> vector3fHashMap;
    HashMap<String, Float> floatHashMap;

    public MappedValues()
    {
        vector3fHashMap = new HashMap<>();
        floatHashMap = new HashMap<>();
    }

    public void addVector3f(String name, Vector3f vector3f) { vector3fHashMap.put(name, vector3f); }

    public void addFloat(String name, Float floatValue) { floatHashMap.put(name, floatValue); }

    public Vector3f getVector3f(String name)
    {
        Vector3f result = vector3fHashMap.get(name);
        if(result != null)
            return result;
        return new Vector3f(0, 0, 0);
    }

    public Float getFloat(String name)
    {
        Float result = floatHashMap.get(name);
        if(result != null)
            return result;
        return 0.0f;
    }
}
