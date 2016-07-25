package com.base.engine.rendering;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class ForwardPoint extends Shader
{
    private static final ForwardPoint instance = new ForwardPoint();

    public ForwardPoint()
    {
        super();
        addVertexShaderFromFile("forward-point.vs");
        addFragmentShaderFromFile("forward-point.fs");
        compileShader();

        setAttribLocation("position", 0);
        setAttribLocation("texCoord", 1);
        setAttribLocation("normal", 2);

        addUniform("model");
        addUniform("MVP");

        addUniform("eyePosition");
        addUniform("specularIntensity");
        addUniform("specularExponent");

        addUniform("pointLight.base.color");
        addUniform("pointLight.base.intensity");
        addUniform("pointLight.atten.constant");
        addUniform("pointLight.atten.linear");
        addUniform("pointLight.atten.exponent");
        addUniform("pointLight.position");
        addUniform("pointLight.range");
    }

    public static ForwardPoint getInstance()
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

        setUniform("pointLight", getRenderingEngine().getPointLight());
    }

    private void setUniform(String uniformName, BaseLight baseLight)
    {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    private void setUniform(String uniformName, PointLight pointLight)
    {
        setUniform(uniformName + ".base", pointLight.getBase());
        setUniformf(uniformName + ".atten.constant", pointLight.getAtten().getConstant());
        setUniformf(uniformName + ".atten.linear", pointLight.getAtten().getLinear());
        setUniformf(uniformName + ".atten.exponent", pointLight.getAtten().getExponent());
        setUniform(uniformName + ".position", pointLight.getPosition());
        setUniformf(uniformName + ".range", pointLight.getRange());
    }
}
