package com.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class MeshResource
{
    private int vao;
    private int vbo;
    private int ebo;
    private int size;

    private int refCount;

    public MeshResource(int size)
    {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        ebo = glGenBuffers();
        this.size = size;

        this.refCount = 1;
    }

    @Override
    protected void finalize()
    {
        glDeleteBuffers(ebo);
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
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

    public int getVao()
    {
        return vao;
    }

    public int getVbo()
    {
        return vbo;
    }

    public int getEbo()
    {
        return ebo;
    }

    public int getSize()
    {
        return size;
    }
}
