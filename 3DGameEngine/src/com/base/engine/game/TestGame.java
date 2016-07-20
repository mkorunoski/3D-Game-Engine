package com.base.engine.game;

import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Camera;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.Vertex;
import com.base.engine.rendering.Window;

public class TestGame implements Game
{
	private Camera camera;		
	
	private GameObject root;
	
	public void init()
	{
		camera = new Camera();	
		
		root = new GameObject();
		
		Transform.setProjection(70.0f, Window.getWidth(), Window.getHeight(), 0.1f, 1000.0f);
		Transform.setCamera(camera);
			
		Vertex[] vertices = new Vertex[]
		{
			new Vertex(new Vector3f(-10, 0, -10), new Vector2f(0, 0)),
			new Vertex(new Vector3f(+10, 0, -10), new Vector2f(1, 0)),
			new Vertex(new Vector3f(+10, 0, +10), new Vector2f(1, 1)),
			new Vertex(new Vector3f(-10, 0, +10), new Vector2f(0, 1)),
		};
		int[] indices = new int[]
		{
			2, 1, 0,
			3, 2, 0
		};
		Mesh mesh = new Mesh(vertices, indices, true);				
		Material material = new Material(new Texture("checkerboard.png"), new Vector3f(1.0f, 1.0f, 1.0f), 1, 256);
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);	
		
		root.addComponent(meshRenderer);
	}
	
	public void input()
	{
		camera.input();
		root.input();
	}
		
	public void update()
	{	
		root.getTransform().setTranslation(new Vector3f(0, -1, 0));
		root.update();		
	}
	
	public void render()
	{
		root.render();				
	}
}