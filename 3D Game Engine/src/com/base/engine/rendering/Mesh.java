package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.meshLoading.IndexedModel;
import com.base.engine.rendering.meshLoading.OBJModel;
import com.base.engine.rendering.resourceManagement.MeshResource;

public class Mesh
{
    public static final int BYTE_SIZE = 4;

    private static HashMap<String, MeshResource> loadedModels = new HashMap<>();
    private MeshResource resource;
    private String filename;

    public Mesh(String filename)
    {
        this.filename = filename;
        MeshResource oldResource = loadedModels.get(filename);

        if(oldResource != null)
        {
            resource = oldResource;
            resource.addReference();
        }
        else
        {
            loadMesh(filename);
            loadedModels.put(filename, resource);
        }
    }

    public Mesh(Vertex[] vertices, int[] indices)
    {
        this(vertices, indices, false);
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals)
    {
        filename = "";
        addVertices(vertices, indices, calcNormals);
    }

    @Override
    protected void finalize()
    {
        if(resource.removeReference() && !filename.isEmpty())
        {
            loadedModels.remove(filename);
        }
    }

    private void addVertices(Vertex[] vertices, int[] indices)
    {
        addVertices(vertices, indices, false);
    }

    private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals)
    {
        if (calcNormals)
        {
            calcNormals(vertices, indices);
        }

        resource = new MeshResource(indices.length);

        glBindVertexArray(resource.getVao());

        glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getEbo());
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * BYTE_SIZE, 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * BYTE_SIZE, 3 * BYTE_SIZE);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * BYTE_SIZE, (3 + 2) * BYTE_SIZE);

        glBindVertexArray(0);
    }

    public void draw()
    {
        glBindVertexArray(resource.getVao());

        glDrawElements(GL_TRIANGLES, resource.getSize(), GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);
    }

    private void calcNormals(Vertex[] vertices, int[] indices)
    {
        for (int i = 0; i < indices.length; i += 3)
        {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
            Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());

            Vector3f normal = v1.cross(v2).normalized();

            vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
            vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
            vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
        }

        for (int i = 0; i < vertices.length; ++i)
        {
            vertices[i].setNormal(vertices[i].getNormal().normalized());
        }
    }

    private Mesh loadMesh(String filename)
    {
        String[] splitArray = filename.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        if (!ext.equals("obj"))
        {
            System.err.println("Error: File format not supported");
            new Exception().printStackTrace();
            System.exit(1);
        }

        OBJModel test = new OBJModel("./res/models/" + filename);
        IndexedModel model = test.toIndexedModel();
        model.calcNormals();

        ArrayList<Vertex> vertices = new ArrayList<>();

        for(int i = 0; i < model.getPositions().size(); ++i)
        {
            vertices.add(new Vertex(model.getPositions().get(i),
                    model.getTexCoords().get(i),
                    model.getNormals().get(i)));
        }

        Vertex[] vertexData = new Vertex[vertices.size()];
        vertices.toArray(vertexData);
        Integer[] indexData = new Integer[model.getIndices().size()];
        model.getIndices().toArray(indexData);

        addVertices(vertexData, Util.toIntArray(indexData), false);

        return null;
    }
}
