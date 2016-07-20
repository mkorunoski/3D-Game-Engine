package com.base.engine;

public class Game
{
	private Camera camera;
	
	private Shader shader;
	
	private Mesh mesh;
	private Material material;
	private Transform transform;	
	
	PointLight pLight1 = new PointLight(new BaseLight(new Vector3f(1, 0.5f, 0), 0.8f), new Attenuation(0, 0, 1), new Vector3f(-2, 0, 0), 10);
	PointLight pLight2 = new PointLight(new BaseLight(new Vector3f(0, 0.5f, 1), 0.8f), new Attenuation(0, 0, 1), new Vector3f(2, 0, 0), 10);
	
	SpotLight sLight1 = new SpotLight(new PointLight(new BaseLight(new Vector3f(0, 1, 1), 0.8f), new Attenuation(0, 0, 0.1f), new Vector3f(0, 1, 0), 30),
									 new Vector3f(0, -1, 0), 0.8f);

	public Game()
	{
		camera = new Camera();
		Transform.setCamera(camera);
		
		shader = PhongShader.getInstance();
		PhongShader.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
		PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3f(1, 1, 1), 0.1f), new Vector3f(1, 1, 1)));
		PhongShader.setPointLights(new PointLight[] { pLight1, pLight2 });
		PhongShader.setSpotLights(new SpotLight[]{ sLight1 });
				
			
		Vertex[] vertices = new Vertex[]
		{
			new Vertex(new Vector3f(-1, 0, -1), new Vector2f(0, 0)),
			new Vertex(new Vector3f(+1, 0, -1), new Vector2f(1, 0)),
			new Vertex(new Vector3f(+1, 0, +1), new Vector2f(1, 1)),
			new Vertex(new Vector3f(-1, 0, +1), new Vector2f(0, 1)),
			
			//new Vertex(new Vector3f(+0, 2, +0), new Vector2f(0.5f, 0.5f))
		};
		int[] indices = new int[]
		{
			2, 1, 0,
			3, 2, 0
			/*2, 1, 0,
			3, 2, 0,			
			0, 1, 4,
			1, 2, 4,
			2, 3, 4,
			3, 0, 4*/
		};
		mesh = new Mesh(vertices, indices, true);
				
		material = new Material(new Texture("checkerboard.png"), new Vector3f(1.0f, 1.0f, 1.0f), 1, 256);
		
		transform = new Transform();
		Transform.setProjection(70.0f, Window.getWidth(), Window.getHeight(), 0.1f, 1000.0f);		
	}
	
	public void input()
	{
		camera.input();
	}
	
	float temp = 0.0f;
	public void update()
	{
		temp += Time.getDelta() / 2;
		float sinTemp = (float)Math.sin(temp);
		
		transform.setTranslation(0, -1, 0);
		//transform.setRotation(0, sinTemp * 180, 0);
		transform.setScale(10, 10, 10);
		
		pLight1.setPosition(new Vector3f(2, 0, 8 * (float)(Math.sin(temp))));
		pLight2.setPosition(new Vector3f(-2, 0, 8 * (float)(Math.cos(temp))));
		
		sLight1.getPointLight().setPosition(camera.getPosition());
		sLight1.setDirection(camera.getForward());
	}
	
	public void render()
	{
		shader.bind();
		shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);		
		mesh.draw();		
	}
}