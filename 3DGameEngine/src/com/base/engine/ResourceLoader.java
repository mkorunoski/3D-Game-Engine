package com.base.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;

import org.newdawn.slick.opengl.TextureLoader;

public class ResourceLoader
{
	public static Texture loadTexture(String filename)
	{
		String[] splitArray = filename.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		try
		{
			int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/textures/" + filename))).getTextureID();
			return new Texture(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
	
	public static String loadShader(String filename)
	{
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		try
		{
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + filename));
			
			String line;
			while((line = shaderReader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");							
			}
			
			shaderReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}		
		
		return shaderSource.toString();
	}
	
	public static Mesh loadMesh(String filename)
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
			
			Mesh res = new Mesh();
			
			Vertex[] vertexData = new Vertex[vertices.size()];
			vertices.toArray(vertexData);
			Integer[] indexData = new Integer[indices.size()];
			indices.toArray(indexData);
			
			res.addVertices(vertexData, Util.toIntArray(indexData));
			
			return res;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
}
