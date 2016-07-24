package com.base.engine.rendering;

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

    public void updateUniforms(Transform transform, Material material)
    {
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture().bind();

        setUniform("model", worldMatrix);
        setUniform("MVP", projectedMatrix);

        setUniform("eyePosition", getRenderingEngine().getMainCamera().getPos());
        setUniformf("specularIntensity", material.getSpecularIntensity());
        setUniformf("specularExponent", material.getSpecularExponent());

        setUniform("directionalLight", getRenderingEngine().getDirectionalLight());
    }

    private void setUniform(String uniformName, BaseLight baseLight)
    {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    private void setUniform(String uniformName, DirectionalLight directionalLight)
    {
        setUniform(uniformName + ".base", directionalLight.getBase());
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }
}
