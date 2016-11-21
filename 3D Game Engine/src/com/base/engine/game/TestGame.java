package com.base.engine.game;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.rendering.*;

public class TestGame extends Game
{
    GameObject pointLightsObject;

    public void init()
    {
        // Camera
        GameObject cameraObject = new GameObject();
        cameraObject.getTransform().setRot(
                new Quaternion((float)Math.toRadians(10), new Vector3f(1, 0, 0))
           .mul(new Quaternion((float)Math.toRadians(180), new Vector3f(0, 1, 0))));
        cameraObject.getTransform().setPos(new Vector3f(0, 5, 10));
        cameraObject.addComponent(new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.1f, 1000.0f));
        cameraObject.addComponent(new FreeLook(0.5f)).addComponent(new FreeMove(10));
        addObject(cameraObject);

        // Directional Light
        GameObject directionalLightObject = new GameObject();
        directionalLightObject.getTransform().setRot(new Quaternion((float)Math.toRadians(45), new Vector3f(-1, 0, 0)));
        directionalLightObject.addComponent(new DirectionalLight(new Vector3f(1, 1, 1), 0.1f));
        addObject(directionalLightObject);

        // Point Lights
        final float D = 10;
        final int NUM_POINTLIGHTS = 4;

        pointLightsObject = new GameObject();

        GameObject pointLightObjects[] = new GameObject[NUM_POINTLIGHTS];
        for(int i = 0; i < NUM_POINTLIGHTS; ++i)
            pointLightObjects[i] = new GameObject();

        pointLightObjects[0].getTransform().setPos(new Vector3f(-D * 0.5f, 2, -D * 0.5f));
        pointLightObjects[1].getTransform().setPos(new Vector3f(+D * 0.5f, 2, -D * 0.5f));
        pointLightObjects[2].getTransform().setPos(new Vector3f(+D * 0.5f, 2, +D * 0.5f));
        pointLightObjects[3].getTransform().setPos(new Vector3f(-D * 0.5f, 2, +D * 0.5f));

        PointLight[] pointLights = new PointLight[NUM_POINTLIGHTS];
        pointLights[0] = new PointLight(new Vector3f(1, 1, 0), 5, new Attenuation(0, 0, 1));
        pointLights[1] = new PointLight(new Vector3f(0, 1, 1), 5, new Attenuation(0, 0, 1));
        pointLights[2] = new PointLight(new Vector3f(1, 0, 1), 5, new Attenuation(0, 0, 1));
        pointLights[3] = new PointLight(new Vector3f(1, 1, 1), 5, new Attenuation(0, 0, 1));

        for(int i = 0; i < NUM_POINTLIGHTS; ++i)
            pointLightObjects[i].addComponent(pointLights[i]);
        for(int i = 0; i < NUM_POINTLIGHTS; ++i)
            pointLightsObject.addChild(pointLightObjects[i]);

        addObject(pointLightsObject);

        // Spot Light
        GameObject spotLightObject = new GameObject();
        spotLightObject.getTransform().setRot(new Quaternion((float)Math.toRadians(45), new Vector3f(1, 0, 0)));
        spotLightObject.getTransform().setPos(new Vector3f(0, 5, -5));
        spotLightObject.addComponent(new SpotLight(new Vector3f(0, 0.5f, 1), 10, new Attenuation(0, 0, 1), 0.7f));
        addObject(spotLightObject);

        // Cubes
        Mesh cubeMesh = new Mesh("cube.obj");
        Material cubeMaterial = new Material();
        cubeMaterial.addTexture("diffuse", new Texture("white.png"));
        cubeMaterial.addFloat("specularIntensity", 0.0f);
        cubeMaterial.addFloat("specularExponent", 0.0f);

        GameObject[] cubeObjects = new GameObject[NUM_POINTLIGHTS + 1];
        for(int i = 0; i < NUM_POINTLIGHTS; ++i)
            cubeObjects[i] = new GameObject();
        for(int i = 0; i < NUM_POINTLIGHTS; ++i)
        {
            cubeObjects[i].getTransform().setScale(new Vector3f(0.4f, 0.4f, 0.4f));
            cubeObjects[i].addComponent(new MeshRenderer(cubeMesh, cubeMaterial));
        }
        for(int i = 0; i < NUM_POINTLIGHTS; ++i)
            pointLightObjects[i].addChild(cubeObjects[i]);

        // Plane
        Material planeMaterial = new Material();
        planeMaterial.addTexture("diffuse", new Texture("concrete.png"));
        planeMaterial.addFloat("specularIntensity", 1.0f);
        planeMaterial.addFloat("specularExponent", 8.0f);
        Mesh planeMesh = new Mesh("plane.obj");

        GameObject planeObject = new GameObject();
        planeObject.getTransform().setScale(new Vector3f(2 * D, 2 * D, 2 * D));
        planeObject.addComponent(new MeshRenderer(planeMesh, planeMaterial));
        planeObject.getTransform().getPos().set(0, 0, 0);
        addObject(planeObject);

        // Fence
        Mesh fenceMesh = new Mesh("fence.obj");
        Material fenceMaterial = new Material();
        fenceMaterial.addTexture("diffuse", new Texture("fenceUV.png"));
        fenceMaterial.addFloat("specularIntensity", 1.0f);
        fenceMaterial.addFloat("specularExponent", 1.0f);

        GameObject fenceObject = new GameObject();
        fenceObject.getTransform().setScale(new Vector3f(2 * D, 2 * D, 2 * D));
        fenceObject.getTransform().setRot(new Quaternion((float)Math.toRadians(90), new Vector3f(1, 0, 0)));
        fenceObject.getTransform().setPos(new Vector3f(0, D, -D));
        fenceObject.addComponent(new MeshRenderer(fenceMesh, fenceMaterial));
        addObject(fenceObject);

        // Monkey
        Material monkeyMaterial = new Material();
        monkeyMaterial.addTexture("diffuse", new Texture("magenta.png"));
        monkeyMaterial.addFloat("specularIntensity", 1.0f);
        monkeyMaterial.addFloat("specularExponent", 8.0f);
        Mesh monkeyMesh = new Mesh("monkey3.obj");

        GameObject monkeyObject = new GameObject();
        monkeyObject.addComponent(new LookAtComponent());
        monkeyObject.getTransform().setScale(new Vector3f(2, 2, 2));
        monkeyObject.getTransform().setPos(new Vector3f(0, 2, 0));
        monkeyObject.addComponent(new MeshRenderer(monkeyMesh, monkeyMaterial));
        addObject(monkeyObject);
    }

    float counter = 0;
    public void update(float delta)
    {
        super.update(delta);
        counter += delta;

        pointLightsObject.getTransform().setRot(new Quaternion(counter, new Vector3f(0,1,0)));
    }
}
