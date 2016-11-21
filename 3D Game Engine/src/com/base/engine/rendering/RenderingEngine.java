package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.core.GameObject;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.resourceManagement.MappedValues;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderingEngine extends MappedValues
{
    private Camera mainCamera;
    private Shader forwardAmbient;

    private ArrayList<BaseLight> lights;
    private BaseLight activeLight;

    private HashMap<String, Integer> samplerMap;

    public RenderingEngine()
    {
        super();
        lights = new ArrayList<>();
        samplerMap = new HashMap<>();

        samplerMap.put("diffuse", 0);
        addVector3f("ambient", new Vector3f(0.25f, 0.25f, 0.25f));

        glClearColor(0, 0, 0, 1);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);
        glEnable(GL_TEXTURE_2D);

        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }

    public int getSamplerSlot(String samplerName)
    {
        return samplerMap.get(samplerName);
    }

    public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType)
    {
        throw new IllegalArgumentException(uniformType + " is not a supported type in RenderingEngine");
    }

    public void addLight(BaseLight light)
    {
        lights.add(light);
    }

    public void addCamera(Camera camera)
    {
        mainCamera = camera;
    }

    public BaseLight getActiveLight()
    {
        return activeLight;
    }

    public void render(GameObject object)
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        forwardAmbient = new Shader("forward-ambient");
        object.renderAll(forwardAmbient, this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        for(BaseLight light : lights)
        {
            activeLight = light;
            object.renderAll(light.getShader(), this);
        }

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);

    }

    public Camera getMainCamera()
    {
        return mainCamera;
    }

    public void setMainCamera(Camera mainCamera)
    {
        this.mainCamera = mainCamera;
    }
}
