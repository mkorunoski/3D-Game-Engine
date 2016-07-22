package com.base.engine.core;

import com.base.engine.rendering.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderingEngine
{
    private Camera mainCamera;

    private Vector3f ambientLight;
    private static DirectionalLight directionalLight;
    private static DirectionalLight directionalLight1;
    private static DirectionalLight directionalLight2;

    public RenderingEngine()
    {
        glClearColor(0, 0, 0, 1);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);
        glEnable(GL_TEXTURE_2D);

        mainCamera = new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.1f, 1000.0f);

        ambientLight = new Vector3f(0.2f, 0.2f, 0.2f);
        directionalLight1 = new DirectionalLight(new BaseLight(new Vector3f(1,0.5f,0), 0.4f), new Vector3f(1,1,1));
        directionalLight2 = new DirectionalLight(new BaseLight(new Vector3f(0,0.5f,1), 0.4f), new Vector3f(-1,1,-1));
    }

    public Vector3f getAmbientLight()
    {
        return ambientLight;
    }

    public DirectionalLight getDirectionalLight()
    {
        return directionalLight;
    }

    private static void clearScreen()
    {
        //TODO: Stencil buffer

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private static void setTextures(boolean enabled)
    {
        if (enabled)
        {
            glEnable(GL_TEXTURE_2D);
        }
        else
        {
            glDisable(GL_TEXTURE_2D);
        }
    }

    public void input(float delta)
    {
        mainCamera.input(delta);
    }

    public void render(GameObject object)
    {
        clearScreen();

        Shader forwardAmbient = ForwardAmbient.getInstance();
        Shader forwardDirectional = ForwardDirectional.getInstance();
        forwardAmbient.setRenderingEngine(this);
        forwardDirectional.setRenderingEngine(this);

        object.render(forwardAmbient);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        directionalLight = directionalLight1;
        object.render(forwardDirectional);
        directionalLight = directionalLight2;
        object.render(forwardDirectional);

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    private static void unbindTextures()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private static String getOpenGLVersion()
    {
        return glGetString(GL_VERSION);
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
