package com.base.engine.rendering.resourceManagement;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderResource
{
    private int program;
    private HashMap<String, Integer> uniforms;
    private ArrayList<String> uniformNames;
    private ArrayList<String> uniformTypes;

    private int refCount;

    public ShaderResource()
    {
        this.program = glCreateProgram();

        if (program == 0)
        {
            System.out.println("Shader creation failed: Valid memory location not found in constructor.");
            System.exit(1);
        }

        uniforms = new HashMap<>();
        uniformNames = new ArrayList<>();
        uniformTypes = new ArrayList<>();

        this.refCount = 1;
    }

    public void addReference()
    {
        refCount++;
    }

    public boolean removeReference()
    {
        refCount--;
        return refCount == 0;
    }

    @Override
    protected void finalize()
    {
        glDeleteBuffers(program);
    }

    public int getProgram()
    {
        return program;
    }

    public HashMap<String, Integer> getUniforms()
    {
        return uniforms;
    }

    public ArrayList<String> getUniformNames()
    {
        return uniformNames;
    }

    public ArrayList<String> getUniformTypes()
    {
        return uniformTypes;
    }
}
