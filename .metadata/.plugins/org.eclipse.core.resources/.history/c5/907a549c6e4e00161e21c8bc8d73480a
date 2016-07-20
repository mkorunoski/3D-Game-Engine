package com.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Mesh
{
	public static final int BYTE_SIZE = 4;
	
	private int vbo;
	private int ibo;
	private int size;
	
	public Mesh(String filename)
	{
		initMeshData();		
		loadMesh(filename);
	}
	
	public Mesh(Vertex[] vertices, int[] indices)
	{
		this(vertices, indices, false);		
	}
	
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals)
	{		
		initMeshData();
		addVertices(vertices, indices, calcNormals);
	}
	
	private void initMeshData()
	{
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		size = 0;		
	}
	
	private void addVertices(Vertex[] vertices, int[] indices)
	{
		addVertices(vertices, indices, false);
	}
	
	private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
		if(calcNormals)
		{
			calcNormals(vertices, indices);
		}
		
		size = indices.length;
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);		
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);		
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * BYTE_SIZE, 0);		
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * BYTE_SIZE, 3 * BYTE_SIZE);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * BYTE_SIZE, (3 + 2) * BYTE_SIZE);		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void draw()
	{	
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		
		glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	private void calcNormals(Vertex[] vertices, int[] indices)
	{
		for(int i = 0; i < indices.length; i += 3)
		{
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];
			
			Vector3f v1 = vertices[i1].getPosition().sub(vertices[i0].getPosition());
			Vector3f v2 = vertices[i2].getPosition().sub(vertices[i0].getPosition());
			
			Vector3f normal = v1.cross(v2).normalized();
			
			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
		}
		
		for(int i = 0; i < vertices.length; ++i)
		{
			vertices[i].setNormal(vertices[i].getNormal().normalized());			
		}
	}
	
	private Mesh loadMesh(String filename)
	{
		String[] splitArray = filename.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		if(!ext.equals("obj"))
		{
			System.err.println("Error: File format not supported");
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		ArrayList<Vertex> vertices = new ArrayList<>();
		ArrayList<Integer> indices = new ArrayList<>();
		
		BufferedReader meshReader = null;
		
		try
		{
			meshReader = new BufferedReader(new FileReader("./res/models/" + filename));
			
			String line;
			while((line = meshReader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);
				
				if(tokens.length == 0 || tokens[0].equals("#"))
				{
					continue;					
				}
				if(tokens[0].equals("v"))
				{
					vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]))));					
				}
				if(tokens[0].equals("f"))
				{
					indices.add(Integer.valueOf(tokens[1]) - 1);
					indices.add(Integer.valueOf(tokens[2]) - 1);
					indices.add(Integer.valueOf(tokens[3]) - 1);
				}				
			}
			
			meshReader.close();
			
			Vertex[] vertexData = new Vertex[vertices.size()];
			vertices.toArray(vertexData);
			Integer[] indexData = new Integer[indices.size()];
			indices.toArray(indexData);
			
			addVertices(vertexData, Util.toIntArray(indexData));			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
}
