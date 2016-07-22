package com.base.engine.rendering;

import com.base.engine.core.Vector3f;

public class PointLight
{
    private BaseLight base;
    private Attenuation atten;
    private Vector3f position;
    private float range;

    public PointLight(BaseLight base, Attenuation atten, Vector3f position, float range)
    {
        this.base = base;
        this.atten = atten;
        this.position = position;
        this.range = range;
    }

    public BaseLight getBase()
    {
        return base;
    }

    public void setBase(BaseLight base)
    {
        this.base = base;
    }

    public Attenuation getAtten()
    {
        return atten;
    }

    public void setAtten(Attenuation atten)
    {
        this.atten = atten;
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    public float getRange()
    {
        return range;
    }

    public void setRange(float range)
    {
        this.range = range;
    }
}
