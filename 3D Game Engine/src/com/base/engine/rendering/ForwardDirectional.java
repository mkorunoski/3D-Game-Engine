package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.DirectionalLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class ForwardDirectional extends Shader
{
    private static final ForwardDirectional instance = new ForwardDirectional();

    public ForwardDirectional()
    {
        super();
        addVertexShaderFromFile("forward-directional.vs");
        addFragmentShaderFromFile("forward-directional.fs");
        compileShader();

        setAttribLocation("position", 0);
        setAttribLocation("texCoord", 1);
        setAttribLocation("normal", 2);

        addUniform("model");
        addUniform("MVP");

        addUniform("eyePosition");
        addUniform("specularIntensity");
        addUniform("specularExponent");

        addUniform("directionalLight.base.color");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");
    }

    public static ForwardDirectional getInstance()
    {
        return instance;
    }

    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
    {
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture("diffuse").bind();

        setUniform("model", worldMatrix);
        setUniform("MVP", projectedMatrix);

        setUniform("eyePosition", renderingEngine.getMainCamera().getTransform().getTransformedPos());
        setUniformf("specularIntensity", material.getFloat("specularIntensity"));
        setUniformf("specularExponent", material.getFloat("specularExponent"));

        setUniformDirectionalLight("directionalLight", (DirectionalLight) renderingEngine.getActiveLight());
    }

    private void setUniformBaseLight(String uniformName, BaseLight baseLight)
    {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    private void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight)
    {
        setUniformBaseLight(uniformName + ".base", directionalLight);
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }
}
