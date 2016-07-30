package com.base.engine.game;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.rendering.*;

public class TestGame extends Game
{
    private GameObject planeObject1;
    private GameObject planeObject2;
    private GameObject monkeyObject;

    public void init()
    {
        // Directional Light Blue
        GameObject directionalLight1 = new GameObject().addComponent(new DirectionalLight(new Vector3f(0,0.5f,1), 0.4f));
        directionalLight1.getTransform().setRot(new Quaternion((float)Math.toRadians(-45), new Vector3f(1,0,0)));
        addObject(directionalLight1);

        // Point Light
        addObject(new GameObject().addComponent(new PointLight(new Vector3f(0,1,0), 0.4f, new Vector3f(0,0,1))));
        // Spot Light
        GameObject spotLightObject = new GameObject();
        SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f, new Vector3f(0,0,0.1f), 0.7f);
        spotLightObject.getTransform().setRot(new Quaternion((float)Math.toRadians(-45), new Vector3f(0,1,0)));
        spotLightObject.addComponent(spotLight);
        addObject(spotLightObject);

        // Plane data
        Material materialPlane = new Material();
        materialPlane.addTexture("diffuse", new Texture("checkerboard.png"));
        materialPlane.addFloat("specularIntensity", 1.0f);
        materialPlane.addFloat("specularExponent", 8.0f);

        int d = 10;
        Vertex[] vertices = new Vertex[]
        {
            new Vertex(new Vector3f(-d, 0, -d), new Vector2f(0, 0)),
            new Vertex(new Vector3f(+d, 0, -d), new Vector2f(1, 0)),
            new Vertex(new Vector3f(+d, 0, +d), new Vector2f(1, 1)),
            new Vertex(new Vector3f(-d, 0, +d), new Vector2f(0, 1))
        };
        int[] indices = new int[] {2, 1, 0, 3, 2, 0};
        Mesh planeMesh = new Mesh(vertices, indices, true);

        // Plane 1
        planeObject1 = new GameObject();
        planeObject1.addComponent(new MeshRenderer(planeMesh, materialPlane));
        planeObject1.getTransform().getPos().set(0, -1, 0);
        addObject(planeObject1);

        // Plane 2
        planeObject2 = new GameObject();
        planeObject2.addComponent(new MeshRenderer(planeMesh, materialPlane));
        planeObject2.getTransform().getPos().set(-5, 2, 5);
        planeObject2.getTransform().getScale().set(0.5f, 1, 0.5f);
        planeObject1.addChild(planeObject2);

        // Monkey
        Mesh tempMesh = new Mesh("monkey3.obj");
        monkeyObject = new GameObject().addComponent(new MeshRenderer(tempMesh, materialPlane));
        addObject(monkeyObject);

        // Camera
        addObject(new GameObject().addComponent(new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.1f, 1000.0f)));
    }

    float counter = 0.0f;
    public void update(float delta)
    {
        super.update(delta);
        counter += delta;
//        planeObject2.getTransform().setPos(new Vector3f(10*(float)Math.sin(counter), 2, 0));
//        planeObject2.getTransform().setRot(new Quaternion(counter, new Vector3f(0, 1, 0)));
    }
}
